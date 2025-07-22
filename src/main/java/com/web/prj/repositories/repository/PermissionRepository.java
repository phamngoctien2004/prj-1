package com.web.prj.repositories.repository;

import com.web.prj.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface PermissionRepository {
    Optional<Permission> findById(Long id);
    Optional<Permission> findByPermissionId(String permissionId);
    Optional<Long> findMaxId();
    Permission save(Permission permission);
    void delete(Long id);
    Page<Permission> findAllPage(Pageable pageable, Specification<Permission> filter);
}
