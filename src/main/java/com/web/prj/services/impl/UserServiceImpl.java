package com.web.prj.services.impl;


import com.web.prj.Helpers.UserHelper;
import com.web.prj.dtos.UserDTO;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.repositories.repository.UserRepository;
import com.web.prj.services.cores.UserService;
import com.web.prj.services.helpers.UserServiceHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserServiceHelper {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleServiceImpl;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleServiceImpl roleServiceImpl
    ) {
        this.userRepository = userRepository;
        this.roleServiceImpl = roleServiceImpl;
    }

    @Override
    public Page<User> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
        String filterValue = filter.orElse("");

        Specification<User> specName = UserHelper.containField("name", filterValue);
        Specification<User> specEmail = UserHelper.containField("email", filterValue);
        Specification<User> specPhone = UserHelper.containField("phone", filterValue);
        Specification<User> spec = specName.or(specEmail).or(specPhone).or(specName);
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User createUser(UserDTO input) {
        Optional<User> existingUser = findByEmail(input.getEmail());

        if(existingUser.isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = new User();
        Long lastUserId = findAccountIdLatest();
        String accountId = "TK" + String.format("%06d", lastUserId + 1);

        user.setAccountId(accountId);
        user.setEmail(input.getEmail());
        user.setRole(roleServiceImpl.findByCode("USER"));
        user.setName(input.getName());
        user.setAvatar(input.getAvatar());
        user.setPhone(input.getPhone());
        userRepository.save(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User updateUser(UserDTO input) {
        User user = userRepository.findById(input.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(user.getPhone() == null || user.getPhone().isBlank()){
            user.setPhone(input.getPhone());
        }

        user.setName(input.getName());
        user.setAvatar(input.getAvatar());
        user.setGender(input.getGender());
        user.setBirth(input.getBirth());
        user.setRole(roleServiceImpl.findByCode(input.getRoleName()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeleted(true);
        userRepository.save(user);
    }



    @Override
    public Long findAccountIdLatest() {

        return userRepository.findTopByOrderByIdDesc()
                .map(User::getId)
                .orElse(0L);
    }

}
