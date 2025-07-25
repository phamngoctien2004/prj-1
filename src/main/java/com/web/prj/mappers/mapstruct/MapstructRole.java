package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructRole {
    RoleResponse toResponse(Role entity);
}
