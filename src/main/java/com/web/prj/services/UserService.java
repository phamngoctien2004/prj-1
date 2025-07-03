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

@Service
@Transactional
public class UserService implements ICrudService<UserDTO> {
    private final UserRepository userRepository;
    private final IUserMapper userMapper;

    public UserService(UserRepository userRepository, IUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTOFull)
                .toList();
    }

    @Override
    public void save(UserDTO data) {
        User user = userMapper.toUser(data);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
