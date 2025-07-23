package com.web.prj.services.user;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user);
    UserDTO grantRole(Long roleId, Long userId);
    void deleteUser(Long id);

    UserDTO getUserDetail(String email);
    PageResponse<UserDTO> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

}
