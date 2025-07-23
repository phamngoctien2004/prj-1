package com.web.prj.services.permission;

import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.entities.GrantedPermission;
import com.web.prj.entities.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    PermissionResponse create(PermissionRequest input);
    PermissionResponse update(PermissionRequest input);

    void delete(Long id);

    List<GrantedPermission> getGrantedByIds(List<Long> id, Role role);
    PageResponse<PermissionResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

}
