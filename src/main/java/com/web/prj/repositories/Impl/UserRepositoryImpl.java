package com.web.prj.repositories.Impl;

import com.web.prj.entities.User;
import com.web.prj.repositories.Jpa.JpaUser;
import com.web.prj.repositories.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUser jpaUserRepository;

    public UserRepositoryImpl(JpaUser jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return jpaUserRepository.existsByPhone(phone);
    }

    @Override
    public Optional<Long> findMaxId() {
        return jpaUserRepository.findMaxId();
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return jpaUserRepository.findAll(spec, pageable);
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    // Additional methods specific to UserRepository can be added here
    // For example, methods to find users by email, etc.
}
