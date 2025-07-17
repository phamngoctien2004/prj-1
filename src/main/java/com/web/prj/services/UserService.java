package com.web.prj.services;


import com.web.prj.Helpers.PageHelper;
import com.web.prj.Helpers.UserHelper;
import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.IUserMapper;
import com.web.prj.repositories.UserRepository;
import com.web.prj.services.interfaces.ICrudService;
import com.web.prj.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements ICrudService<UserDTO, User>, IUserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final IUserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            IUserMapper userMapper,
            RoleService roleService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @Override
    public PageResponse<List<UserDTO>> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
        String filterValue = filter.orElse("");

        Specification<User> specName = UserHelper.containField("name", filterValue);
        Specification<User> specEmail = UserHelper.containField("email", filterValue);
        Specification<User> specPhone = UserHelper.containField("phone", filterValue);
        Specification<User> spec = specName.or(specEmail).or(specPhone).or(specName);
        return PageHelper.toPageResponse(userRepository.findAll(spec,pageable)
                .map(this::toDto));
    }

    @Override
    public List<UserDTO> findAll() {
        return null;
    }

    @Override
    public void saveDTO(UserDTO data) {
        User user = userMapper.toUser(data);
        userRepository.save(user);
    }

    @Override
    public void save(User data) {
        userRepository.save(data);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User data) {
        Long lastUserId = findAccountIdLatest();
        String accountId = "TK" + String.format("%06d", lastUserId + 1);

        User user = new User();
        user.setEmail(data.getEmail());
        user.setAccountId(accountId);
        user.setName(data.getName());
        user.setAvatar(data.getAvatar());
        user.setRole(roleService.findByCode("USER"));
        return userRepository.save(user);
    }

    @Override
    public UserDTO findById(Long id) {
        return toDto(userRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return toDto(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
    }

    @Override
    public Long findAccountIdLatest() {

        return userRepository.findTopByOrderByIdDesc()
                .map(User::getId)
                .orElse(0L);
    }

    @Override
    public ApiResponse<UserDTO> myInfo(String email) {
        return ApiResponse.<UserDTO>builder()
                .code("200")
                .success(true)
                .data(findUserByEmail(email))
                .build();
    }

    public UserDTO toDto(User user) {
        return userMapper.toDTOFull(user);
    }
}
