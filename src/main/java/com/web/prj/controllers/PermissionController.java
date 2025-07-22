package com.web.prj.controllers;

import com.web.prj.dtos.request.PermissionRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.PermissionResponse;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.PermissionMapper;
import com.web.prj.repositories.repository.PermissionRepository;
import com.web.prj.services.permission.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/permission")
public class PermissionController {
    private final PermissionService service;
    private final PermissionMapper permissionMapper;
    private final PermissionRepository repository;
    @GetMapping
    public ResponseEntity<?> getPermission(Pageable pageable, Optional<String> filter) {
        PageResponse<PermissionResponse> response = permissionMapper.toPageResponse(
                service.findAllByPageAndFilter(pageable, filter)
        );

        return ResponseEntity.ok(
                new ApiResponse<>(response, "Lấy danh sách quyền thành công")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        PermissionResponse response = permissionMapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND))
        );
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Lấy quyền thành công")
        );
    }

    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse response = permissionMapper.toResponse(
                service.create(request)
        );
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Tạo quyền thành công")
        );
    }

    @PutMapping
    public ResponseEntity<?> updatePermission(@RequestBody PermissionRequest request) {
        PermissionResponse response = permissionMapper.toResponse(
                service.update(request)
        );
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Cập nhật thành công")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(null, "Xóa quyền thành công")
        );
    }
}
