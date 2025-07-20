package com.web.prj.repositories.repository;

import com.web.prj.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UserRepository{
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    Optional<User> findTopByOrderByIdDesc();
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    void save(User user);
}
