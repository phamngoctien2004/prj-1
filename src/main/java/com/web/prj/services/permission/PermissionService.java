package com.web.prj.services.permission;

import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.entities.GrantedPermission;
import com.web.prj.entities.Permission;
import com.web.prj.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Permission create(PermissionRequest input);
    Permission update(PermissionRequest input);

    void delete(Long id);

    List<GrantedPermission> getGrantedByIds(List<Long> id, Role role);
    Page<Permission> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

}
