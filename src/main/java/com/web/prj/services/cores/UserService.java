package com.web.prj.services.cores;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

    Optional<User> findByEmail(String email);
    User createUser(UserDTO user);
    User updateUser(UserDTO user);
    void deleteUser(Long id);
}
