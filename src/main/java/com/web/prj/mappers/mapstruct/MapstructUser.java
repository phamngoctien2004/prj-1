package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.UserDTO;
import com.web.prj.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructUser{
    User toEntity(UserDTO dto);
    UserDTO toDto(User entity);
}
