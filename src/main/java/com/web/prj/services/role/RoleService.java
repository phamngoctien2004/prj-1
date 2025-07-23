package com.web.prj.services.role;

import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    RoleResponse createRole(RoleRequest input);
    RoleResponse updateRole(RoleRequest input);
    void deleteRole(Long id);
    PageResponse<RoleResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter);
    RoleResponse getRoleDetail(Long id);
}
