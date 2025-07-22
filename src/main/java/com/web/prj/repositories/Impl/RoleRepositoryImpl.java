package com.web.prj.repositories.Impl;

import com.web.prj.entities.Role;
import com.web.prj.enums.ROLE;
import com.web.prj.repositories.Jpa.JpaRole;
import com.web.prj.repositories.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRole repository;
    public RoleRepositoryImpl(JpaRole repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findByRoleId(String roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Long> findMaxId() {
        return repository.findMaxId();
    }

    @Override
    public Page<Role> findAll(Pageable pageable, Specification<Role> spec) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
