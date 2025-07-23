package com.web.prj.services.permission;

import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.entities.GrantedPermission;
import com.web.prj.entities.Permission;
import com.web.prj.entities.Role;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.PermissionMapper;
import com.web.prj.repositories.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository repository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest input) {
        String id = input.getModule() + "_" + input.getAction();

        if (repository.findByPermissionId(id).isPresent()) {
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }
        Permission permission = Permission.builder()
                .permissionId(id.toLowerCase())
                .name(input.getName())
                .module(input.getModule())
                .action(input.getAction())
                .active(true)
                .build();
        return permissionMapper.toResponse(repository.save(permission));
    }

    @Override
    public PermissionResponse update(PermissionRequest input) {
        Permission permission = repository.findById(input.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        permission.setName(input.getName());
        permission.setActive(input.isActive());
        return permissionMapper.toResponse(repository.save(permission));
    }


    @Override
    public void delete(Long id) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        if (!permission.getGrantedPermissions().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }

        repository.delete(id);
    }

    @Override
    public List<GrantedPermission> getGrantedByIds(List<Long> ids, Role role) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return ids.stream()
                .map(id -> {
                            Permission permission = repository.findById(id)
                                    .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
                            return GrantedPermission.builder()
                                    .permission(permission)
                                    .role(role)
                                    .build();
                        }
                ).toList();
    }

    @Override
    public PageResponse<PermissionResponse> findAllByPageAndFilter(Pageable pageable, Optional<String> filter) {
        Specification<Permission> specName = SpecHelper.containField("name", filter.orElse(""));
        Specification<Permission> specModule = SpecHelper.containField("module", filter.orElse(""));
        Specification<Permission> specAction = SpecHelper.containField("action", filter.orElse(""));
        Specification<Permission> spec = specName.or(specModule).or(specAction);
        return permissionMapper.toPageResponse(
                repository.findAllPage(pageable, spec)
        );
    }
}
