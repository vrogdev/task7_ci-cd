package com.epam.esm.security.permission;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoleAuthority {
    USER(permissionSetOf(
            Permission.READ,
            Permission.PLACE_ORDER)),
    ADMIN(permissionSetOf(
            Permission.READ,
            Permission.PLACE_ORDER,
            Permission.WRITE));

    private final Set<Permission> permissions;

    RoleAuthority(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public static Set<SimpleGrantedAuthority> getAuthoritiesForUser(User user) {
        RoleAuthority authority = valueOf(user.getRole().name());

        return authority.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
    private static Set<Permission> permissionSetOf(Permission... permissions) {
        return new HashSet<>(Arrays.asList(permissions));
    }

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        RoleAuthority authority = valueOf(role.name());

        return authority.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}
