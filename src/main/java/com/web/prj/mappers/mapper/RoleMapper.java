package com.web.prj.mappers.mapper;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Role;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;

// đdddinh nghĩa hành vi của mapper
public interface RoleMapper {
    RoleResponse toResponse(Role entity);

    PageResponse<RoleResponse> toPageResponse(Page<Role> page);
}
