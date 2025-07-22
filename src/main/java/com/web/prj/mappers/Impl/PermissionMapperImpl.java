package com.web.prj.mappers.Impl;

import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.entities.Permission;
import com.web.prj.mappers.mapper.PermissionMapper;
import com.web.prj.mappers.mapstruct.MapstructPermission;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionMapperImpl implements PermissionMapper {
    private final MapstructPermission mapstruct;

    public PermissionMapperImpl(MapstructPermission mapstruct) {
        this.mapstruct = mapstruct;
    }
    @Override
    public PermissionResponse toResponse(Permission permission) {
        return mapstruct.toResponse(permission);
    }

    @Override
    public PageResponse<PermissionResponse> toPageResponse(Page<Permission> page) {
        List<PermissionResponse> content = page.getContent().stream()
                .map(mapstruct::toResponse)
                .toList();
        return PageResponse.<PermissionResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
