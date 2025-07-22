package com.web.prj.Helpers;

import com.web.prj.entities.Role;
import com.web.prj.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleId()));

        role.getGrantedPermissions()
                .forEach(grantedPermission -> {
                    String permission = grantedPermission.getPermission().getModule() + ":" + grantedPermission.getPermission().getAction();
                    authorities.add(new SimpleGrantedAuthority(permission));
                });
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
