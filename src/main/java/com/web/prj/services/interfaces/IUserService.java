package com.web.prj.services.interfaces;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDTO findById(Long id);
    UserDTO findUserByEmail(String email);

    PageResponse<List<UserDTO>> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

    Long findAccountIdLatest();
    ApiResponse<UserDTO> myInfo(String email);
    User createUser(User user);
}
