package com.web.prj.services.role;

import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    Role createRole(RoleRequest input);
    Role updateRole(RoleRequest input);
    void deleteRole(Long id);
    Page<Role> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);
    Role getRoleDetail(Long id);
}
