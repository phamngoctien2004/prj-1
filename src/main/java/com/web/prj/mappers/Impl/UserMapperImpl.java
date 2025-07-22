package com.web.prj.mappers.Impl;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import com.web.prj.mappers.mapstruct.MapstructUser;
import com.web.prj.mappers.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    private final MapstructUser mapstructUser;

    public UserMapperImpl(MapstructUser mapstructUser) {
        this.mapstructUser = mapstructUser;
    }
    @Override
    public User toEntity(UserDTO dto) {
        return mapstructUser.toEntity(dto);
    }
    @Override
    public UserDTO toDto(User entity) {
        UserDTO output = mapstructUser.toDto(entity);
        output.setRoleName(entity.getRole().getRoleId());
        return output;
    }

    @Override
    public PageResponse<UserDTO> toPageResponse(Page<User> page) {
        List<UserDTO> content = page.getContent().stream()
                .map(this::toDto)
                .toList();

        return PageResponse.<UserDTO>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
