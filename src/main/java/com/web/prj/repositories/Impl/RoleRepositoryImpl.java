package com.web.prj.repositories.Impl;

import com.web.prj.entities.Role;
import com.web.prj.repositories.Jpa.JpaRoleRepository;
import com.web.prj.repositories.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository repository;
    public RoleRepositoryImpl(JpaRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findByRoleId(String code) {
        return repository.findByRoleId(code);
    }
}
