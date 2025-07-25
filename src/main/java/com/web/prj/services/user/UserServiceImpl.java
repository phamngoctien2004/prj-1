package com.web.prj.services.user;


import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.UserResponse;
import com.web.prj.entities.Role;
import com.web.prj.entities.User;
import com.web.prj.enums.ROLE;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.UserMapper;
import com.web.prj.repositories.repository.RoleRepository;
import com.web.prj.repositories.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest input) {
        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());

        if (existingUser.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toEntity(input);
        user.setRole(
                roleRepository.findByRoleId(ROLE.USER.getLevel()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND))
        );

        String phone = input.getPhone();
        if(userRepository.existsByPhone(phone)) {
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }

        Long lastUserId = userRepository.findMaxId().orElse(0L);
        String accountId = "TK" + String.format("%06d", lastUserId + 1);
        user.setAccountId(accountId);

        return userMapper.toResponse(userRepository.save(user));
    }


    @Override
    public UserResponse updateUser(UserRequest input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Role role = roleRepository.findById(input.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (input.getAvatar() != null && !input.getAvatar().isEmpty()) {
            user.setAvatar(input.getAvatar());
        }
        user.setName(input.getName());
        user.setGender(input.getGender());
        user.setBirth(input.getBirth());
        user.setPhone(input.getPhone());
        user.setActive(input.isActive());
        user.setAddress(input.getAddress());
        user.setRole(role);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(user.getOrders() != null && !user.getOrders().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }
        userRepository.delete(id);
    }


    @Override
    public UserResponse getUserDetail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }


    @Override
    public PageResponse<UserResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
        String filterValue = filter.orElse("");

        Specification<User> specName = SpecHelper.containField("name", filterValue);
        Specification<User> specEmail = SpecHelper.containField("email", filterValue);
        Specification<User> specPhone = SpecHelper.containField("phone", filterValue);
        Specification<User> spec = specName.or(specEmail).or(specPhone).or(specName);
        return userMapper.toPageResponse(
                userRepository.findAll(spec, pageable)
        );
    }
}
