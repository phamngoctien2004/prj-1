package com.web.prj.repositories.Impl;

import com.web.prj.entities.Permission;
import com.web.prj.repositories.Jpa.JpaPermission;
import com.web.prj.repositories.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final JpaPermission jpaPermission;

    @Override
    public Optional<Permission> findById(Long id) {
        return jpaPermission.findById(id);
    }

    @Override
    public Optional<Permission> findByPermissionId(String permissionId) {
        return jpaPermission.findByPermissionId(permissionId);
    }

    @Override
    public Optional<Long> findMaxId() {
        return jpaPermission.findMaxId();
    }



    @Override
    public Permission save(Permission permission) {
        return jpaPermission.save(permission);
    }

    @Override
    public void delete(Long id) {
        jpaPermission.deleteById(id);
    }

    @Override
    public Page<Permission> findAllPage(Pageable pageable, Specification<Permission> spec) {
        return jpaPermission.findAll(spec, pageable);
    }
}
