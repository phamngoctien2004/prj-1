package com.web.prj.mappers.mapper;

import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.UserResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;

// đdddinh nghĩa hành vi của mapper
public interface UserMapper{
    User toEntity(UserRequest dto);
    UserResponse toResponse(User entity);

    PageResponse<UserResponse> toPageResponse(Page<User> page);
}
