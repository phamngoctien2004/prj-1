package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Permission;
import com.web.prj.entities.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructPermission {
    PermissionResponse toResponse(Permission entity);
}
