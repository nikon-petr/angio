package com.angio.angiobackend.init;

import com.angio.angiobackend.api.security.entity.Permission;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.repository.PermissionRepository;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialSecurityDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final String ROOT_UUID = "c28e0d38-410b-11e9-b28d-0242ac130002";
    private static final String ADMIN_UUID = "c28e3268-410b-11e9-b28d-0242ac130002";
    private static final String SGMU_DOCTOR_UUID = "c28e3308-410b-11e9-b28d-0242ac130002";
    private static final String SINGLE_DOCTOR_UUID = "c28e3358-410b-11e9-b28d-0242ac130002";

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserService userService;

    /**
     * Update permissions roles and user references on it when application starts.
     *
     * @param event event
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("onApplicationEvent() - start");
        log.info("Load roles and permissions");

        if (alreadySetup) {
            log.debug("onApplicationEvent() - already setup");
            return;
        }

        log.debug("onApplicationEvent() - load roles and permissions");
        Map<Roles, Role> allRoles = new HashMap<>();

        for (Roles role : Roles.values()) {

            Set<Permission> permissions = new HashSet<>();
            for (Permissions permission : role.getPermissions()) {
                log.debug("onApplicationEvent() - create if not found permission {}", permission.name());
                permissions.add(createPermissionIfNotFound(permission));
            }

            log.debug("onApplicationEvent() - create if not found role {}", role.name());
            allRoles.put(role, createRoleIfNotFound(role, permissions));
        }
        log.debug("onApplicationEvent() - loaded roles: {}", allRoles);

        log.debug("onApplicationEvent() - attach roles to users");
        User rootUser = userRepository.findById(UUID.fromString(ROOT_UUID))
                .orElseThrow(() -> new IllegalStateException("Super user is absent"));
        Optional<User> adminUser = userRepository.findById(UUID.fromString(ADMIN_UUID));
        Optional<User> sgmuExampleDoctorUser = userRepository.findById(UUID.fromString(SGMU_DOCTOR_UUID));
        Optional<User> singleDoctorUser = userRepository.findById(UUID.fromString(SINGLE_DOCTOR_UUID));

        log.debug("onApplicationEvent() - attach manged roles to user");
        rootUser.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles, Roles.values()));
        adminUser.ifPresent(u -> u.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles, Roles.DOCTOR)));
        adminUser.ifPresent(u -> u.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles)));

        log.debug("onApplicationEvent() - attach roles to users");
        rootUser.getRoles()
                .add(allRoles.get(Roles.ROOT));
        adminUser.ifPresent(u -> u.getRoles()
                .add(allRoles.get(Roles.ADMIN)));
        sgmuExampleDoctorUser.ifPresent(u -> u.getRoles()
                .add(allRoles.get(Roles.DOCTOR)));
        singleDoctorUser.ifPresent(u -> u.getRoles()
                .add(allRoles.get(Roles.SINGLE_DOCTOR)));

        adminUser.ifPresent(userRepository::save);
        sgmuExampleDoctorUser.ifPresent(userRepository::save);
        singleDoctorUser.ifPresent(userRepository::save);

        resetRootUser(rootUser);

        alreadySetup = true;
        log.debug("onApplicationEvent() - end");
    }

    private void resetRootUser(User root) {
        if(StringUtils.isEmpty(root.getPassword()) && !root.isEnabled()) {
            userService.resetRootPassword(root.getEmail());
        }
    }

    private Permission createPermissionIfNotFound(Permissions permission) {

        Optional<Permission> nullablePermission = permissionRepository.findByName(permission.name());

        if (!nullablePermission.isPresent()) {
            Permission newPermission = new Permission()
                    .setName(permission.name())
                    .setDescription(permission.getDescription());
            newPermission = permissionRepository.save(newPermission);
            nullablePermission = Optional.of(newPermission);
        }

        return nullablePermission.get();
    }

    private Role createRoleIfNotFound(Roles role, Collection<Permission> permissions) {

        Optional<Role> nullableRole = roleRepository.findByName(role.name());

        if (nullableRole.isPresent()) {
            nullableRole.get().getPermissions().removeIf(e -> !permissions.contains(e));
            nullableRole.get().getPermissions().addAll(permissions);
        }

        if (!nullableRole.isPresent()) {
            Role newRole = new Role()
                    .setName(role.name())
                    .setDescription(role.getDescription())
                    .setSystemRole(role.isSystemRole())
                    .setPermissions(new HashSet<>(permissions));
            newRole = roleRepository.save(newRole);
            nullableRole = Optional.of(newRole);
        }

        return nullableRole.get();
    }

    private List<Role> extractRoleEntitiesFor(Map<Roles, Role> roleStorage, Roles... roles) {
        List<Role> result = new ArrayList<>();

        for (Roles role : roles) {
            result.add(roleStorage.get(role));
        }

        return result;
    }
}
