package com.web.prj.repositories.Jpa;

import com.web.prj.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPermission extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    @Query("SELECT MAX(p.id) From Permission p")
    Optional<Long> findMaxId();

    Optional<Permission> findByPermissionId(String permissionId);
}
