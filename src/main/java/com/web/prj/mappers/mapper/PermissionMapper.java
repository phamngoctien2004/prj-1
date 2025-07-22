package com.web.prj.mappers.mapper;

import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Permission;
import com.web.prj.entities.Role;
import org.springframework.data.domain.Page;

public interface PermissionMapper {
    PermissionResponse toResponse(Permission permission);
    PageResponse<PermissionResponse> toPageResponse(Page<Permission> page);

}
