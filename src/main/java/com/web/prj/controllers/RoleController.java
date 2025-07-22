package com.web.prj.controllers;

import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.entities.Role;
import com.web.prj.mappers.mapper.RoleMapper;
import com.web.prj.services.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    @GetMapping
    public ResponseEntity<?> getRole(Pageable pageable, @RequestParam("filter") Optional<String> filter) {
        PageResponse<RoleResponse> response = roleMapper
                .toPageResponse(roleService.findAllByPageAndFilter(pageable, filter));
        return ResponseEntity.ok(
                new ApiResponse<PageResponse<RoleResponse>>(response, "Lấy dữ liệu thành công")
        );
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        RoleResponse response = roleMapper.toResponse(roleService.createRole(request));
        return ResponseEntity.ok(
                new ApiResponse<RoleResponse>(response, "Tạo mới thành công")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleDetail(id);
        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody RoleRequest request) {
        RoleResponse response = roleMapper.toResponse(roleService.updateRole(request));
        return ResponseEntity.ok(
                new ApiResponse<RoleResponse>(response, "Cập nhật thành công")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(
                new ApiResponse<>(null, "Xóa thành công")
        );
    }

}
