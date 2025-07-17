package com.web.prj.mappers;

import com.web.prj.dtos.BaseDTO;
import com.web.prj.dtos.UserDTO;
import com.web.prj.entities.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDTO toDTO(User user);

    User toUser(UserDTO userDTO);

    default UserDTO toDTOFull(User user) {
        UserDTO userDTO = toDTO(user);
        userDTO.setRoleName(user.getRole().getRoleId());
        return userDTO;
    }

}
