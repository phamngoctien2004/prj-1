package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.response.UserResponse;
import com.web.prj.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructUser{
    User toEntity(UserRequest dto);
    UserResponse toResponse(User entity);
}
