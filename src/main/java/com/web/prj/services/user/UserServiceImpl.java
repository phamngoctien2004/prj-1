package com.web.prj.services.user;


import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.Role;
import com.web.prj.entities.User;
import com.web.prj.enums.ROLE;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.UserMapper;
import com.web.prj.repositories.repository.RoleRepository;
import com.web.prj.repositories.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public UserDTO createUser(UserDTO input) {
        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());

        if (existingUser.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toEntity(input);

        Long lastUserId = userRepository.findMaxId().orElse(0L);
        String accountId = "TK" + String.format("%06d", lastUserId + 1);
        user.setAccountId(accountId);

        user.setRole(
                roleRepository.findByRoleId(ROLE.USER.getLevel()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND))
        );


        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    public UserDTO updateUser(UserDTO input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setName(input.getName());
        user.setAvatar(input.getAvatar());
        user.setGender(input.getGender());
        user.setBirth(input.getBirth());
        user.setPhone(input.getPhone());
        user.setStatus(input.getStatus());
        user.setAddress(input.getAddress());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(id);
    }

    @Override
    public UserDTO getUserDetail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO grantRole(Long roleId, Long userId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setRole(role);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public PageResponse<UserDTO> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
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
