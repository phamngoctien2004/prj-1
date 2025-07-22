package com.web.prj.mappers.Impl;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Role;
import com.web.prj.mappers.mapper.PermissionMapper;
import com.web.prj.mappers.mapper.RoleMapper;
import com.web.prj.mappers.mapstruct.MapstructPermission;
import com.web.prj.mappers.mapstruct.MapstructRole;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RoleMapperImpl implements RoleMapper {
    private final MapstructRole mapstructRole;
    private final PermissionMapper permissionMapper;

    @Override
    public RoleResponse toResponse(Role entity) {
        RoleResponse response = mapstructRole.toResponse(entity);

        response.setPermissions(
                entity.getGrantedPermissions().stream()
                        .map(granted -> permissionMapper.toResponse(granted.getPermission()))
                        .toList()
        );
        return response;
    }

    @Override
    public PageResponse<RoleResponse> toPageResponse(Page<Role> page) {
        List<RoleResponse> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();
        return PageResponse.<RoleResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
