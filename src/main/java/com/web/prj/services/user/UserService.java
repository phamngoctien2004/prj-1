package com.web.prj.services.user;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(UserDTO user);
    User updateUser(UserDTO user);
    User grantRole(Long roleId, Long userId);
    void deleteUser(Long id);

    Page<User> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

}
