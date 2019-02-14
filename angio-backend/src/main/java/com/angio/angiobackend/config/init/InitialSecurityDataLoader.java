package com.angio.angiobackend.config.init;

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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
                permissions.add(createPermissionIfNotFound(permission.name(), permission.getDescription()));
            }

            log.debug("onApplicationEvent() - create if not found role {}", role.name());
            allRoles.put(role, createRoleIfNotFound(role.name(), permissions));
        }
        log.debug("onApplicationEvent() - loaded roles: {}", allRoles);

        log.debug("onApplicationEvent() - attach roles to users");
        User adminUser = userRepository.findByEmail("admin@example.com").get();
        User doctorUser = userRepository.findByEmail("doctor@example.com").get();

        adminUser.getRoles()
                .add(allRoles.get(Roles.ADMIN));
        doctorUser.getRoles()
                .add(allRoles.get(Roles.DOCTOR));

        userRepository.save(adminUser);
        userRepository.save(doctorUser);

        alreadySetup = true;
        log.debug("onApplicationEvent() - end");
    }

    private Permission createPermissionIfNotFound(String name, String description) {

        Optional<Permission> nullablePermission = permissionRepository.findByName(name);

        if (!nullablePermission.isPresent()) {
            Permission permission = new Permission()
                    .setName(name)
                    .setDescription(description);
            permission = permissionRepository.save(permission);
            nullablePermission = Optional.of(permission);
        }

        return nullablePermission.get();
    }

    private Role createRoleIfNotFound(String name, Collection<Permission> permissions) {

        Optional<Role> nullableRole = roleRepository.findByName(name);

        if (nullableRole.isPresent()) {
            nullableRole.get().getPermissions().removeIf(e -> !permissions.contains(e));
            nullableRole.get().getPermissions().addAll(permissions);
        }

        if (!nullableRole.isPresent()) {
            Role role = new Role()
                    .setName(name)
                    .setPermissions(new HashSet<>(permissions));
            role = roleRepository.save(role);
            nullableRole = Optional.of(role);
        }

        return nullableRole.get();
    }
}
