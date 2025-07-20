package com.web.prj.mappers.mapper;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;

// đdddinh nghĩa hành vi của mapper
public interface UserMapper{
    User toEntity(UserDTO dto);
    UserDTO toDto(User entity);

    PageResponse<UserDTO> toPageResponse(Page<User> page);
}
