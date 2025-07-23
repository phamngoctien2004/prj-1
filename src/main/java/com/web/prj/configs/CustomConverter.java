package com.web.prj.configs;

import com.web.prj.entities.Role;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.repositories.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CustomConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final RoleRepository repository;
    @Override
    @Transactional
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String roleId = jwt.getClaimAsString("role");
        String email = jwt.getSubject();

        Role role = repository.findByRoleId(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(roleId));
        role.getGrantedPermissions().forEach(gp -> {
            String perm = gp.getPermission().getPermissionId();
            authorities.add(new SimpleGrantedAuthority(perm));
        });
        return new JwtAuthenticationToken(jwt, authorities, email); // hoặc jwt.getClaim("username")
    }
}
