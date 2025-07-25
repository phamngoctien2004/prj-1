package com.web.prj.mappers.Impl;

import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.UserResponse;
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
    public User toEntity(UserRequest dto) {
        return mapstructUser.toEntity(dto);
    }
    @Override
    public UserResponse toResponse(User entity) {
        UserResponse output = mapstructUser.toResponse(entity);
        output.setRoleId(entity.getRole().getRoleId());
        return output;
    }

    @Override
    public PageResponse<UserResponse> toPageResponse(Page<User> page) {
        List<UserResponse> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PageResponse.<UserResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
