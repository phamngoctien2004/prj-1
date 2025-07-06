package com.web.prj.services;


import com.web.prj.dtos.UserDTO;
import com.web.prj.entities.User;
import com.web.prj.mappers.IUserMapper;
import com.web.prj.repositories.UserRepository;
import com.web.prj.services.interfaces.ICrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements ICrudService<UserDTO, User> {
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
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTOFull)
                .toList();
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

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User createUser(User data){
        User user = new User();
        user.setEmail(data.getEmail());
        user.setCode("USER_" + data.getEmail());
        user.setName(data.getName());
        user.setAvatar(data.getAvatar());
        user.setRole(roleService.findByCode("USER"));
        return userRepository.save(user);
    }
    public UserDTO toDto(User user){
        return userMapper.toDTOFull(user);
    }
}
