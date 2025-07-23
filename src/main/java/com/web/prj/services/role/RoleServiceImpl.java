package com.web.prj.services.role;

import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.GrantedPermission;
import com.web.prj.entities.Role;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.RoleMapper;
import com.web.prj.repositories.repository.RoleRepository;
import com.web.prj.services.permission.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final PermissionService permissionService;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest input) {
        Optional<Role> existingRole = repository.findByRoleId(input.getRoleId());

        if (existingRole.isPresent()) {
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }

        Role role = Role.builder()
                .roleId(input.getRoleId())
                .name(input.getName())
                .active(true)
                .build();
        List<GrantedPermission> granted = permissionService.getGrantedByIds(input.getPermissionIds(), role);

        role.setGrantedPermissions(granted);
        return roleMapper.toResponse(repository.save(role));
    }

    @Override
    public RoleResponse updateRole(RoleRequest input) {
        Role role = repository.findById(input.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        role.setName(input.getName());
        List<GrantedPermission> granted = permissionService.getGrantedByIds(input.getPermissionIds(), role);
        role.getGrantedPermissions().clear();
        role.getGrantedPermissions().addAll(granted != null ? granted : new ArrayList<>());
        role.setActive(input.getActive());
        return roleMapper.toResponse(repository.save(role));
    }

    @Override
    public void deleteRole(Long id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        if (!role.getGrantedPermissions().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }

        repository.delete(id);
    }

    @Override
    public PageResponse<RoleResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
        Specification<Role> specName = SpecHelper.containField("name", filter.orElse(""));
        Specification<Role> specRoleId = SpecHelper.containField("roleId", filter.orElse(""));
        Specification<Role> spec = specName.or(specRoleId);
        return roleMapper.toPageResponse(
                repository.findAll(pageable, spec)
        );
    }

    @Override
    public RoleResponse getRoleDetail(Long id) {
        return roleMapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND))
        );
    }

}
