package com.web.prj.services.user;

import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest user);
    UserResponse updateUser(UserRequest user);
    UserResponse grantRole(Long roleId, Long userId);
    void deleteUser(Long id);

    UserResponse applyMember(Long memberId, Long userId);
    UserResponse getUserDetail(String email);
    PageResponse<UserResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);

}
