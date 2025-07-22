package com.web.prj.repositories.repository;

import com.web.prj.entities.Role;
import com.web.prj.enums.ROLE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleId(String code);
    Optional<Role> findById(Long id);

    Optional<Long> findMaxId();

    Page<Role> findAll(Pageable pageable, Specification<Role> spec);
    Role save(Role role);

    void delete(Long id);
}
