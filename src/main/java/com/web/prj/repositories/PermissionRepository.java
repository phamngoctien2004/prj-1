package com.web.prj.repositories;

import com.web.prj.entities.Permission;
import com.web.prj.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
