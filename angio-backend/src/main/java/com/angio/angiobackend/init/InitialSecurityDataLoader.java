package com.angio.angiobackend.init;

import com.angio.angiobackend.api.security.entity.Permission;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.security.repository.PermissionRepository;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class InitialSecurityDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public InitialSecurityDataLoader(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

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
        User rootUser = userRepository.findByEmail("root@example.com").get();
        User adminUser = userRepository.findByEmail("admin@example.com").get();
        User sgmuExampleDoctorUser = userRepository.findByEmail("sgmu.doctor@example.com").get();
        User singleDoctorUser = userRepository.findByEmail("single.doctor@example.com").get();

        log.debug("onApplicationEvent() - attach manged roles to user");
        rootUser.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles, Roles.values()));
        adminUser.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles, Roles.DOCTOR));
        adminUser.getOwnedRolesToManage().addAll(extractRoleEntitiesFor(allRoles));

        log.debug("onApplicationEvent() - attach roles to users");
        rootUser.getRoles()
                .add(allRoles.get(Roles.ROOT));
        adminUser.getRoles()
                .add(allRoles.get(Roles.ADMIN));
        sgmuExampleDoctorUser.getRoles()
                .add(allRoles.get(Roles.DOCTOR));
        singleDoctorUser.getRoles()
                .add(allRoles.get(Roles.SINGLE_DOCTOR));

        userRepository.save(adminUser);
        userRepository.save(sgmuExampleDoctorUser);
        userRepository.save(singleDoctorUser);

        alreadySetup = true;
        log.debug("onApplicationEvent() - end");
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
