package com.web.prj.controllers;

import com.web.prj.dtos.request.RoleRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.RoleResponse;
import com.web.prj.services.role.RoleService;
import jakarta.validation.Valid;
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
    @GetMapping
    public ResponseEntity<?> getRole(Pageable pageable, @RequestParam("filter") Optional<String> filter) {
        PageResponse<RoleResponse> response = roleService.findAllByPageAndFilter(pageable, filter);
        return ResponseEntity.ok(
                new ApiResponse<PageResponse<RoleResponse>>(response, "Lấy dữ liệu thành công")
        );
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.ok(
                new ApiResponse<RoleResponse>(response, "Tạo mới thành công")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        RoleResponse response = roleService.getRoleDetail(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleService.updateRole(request);
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
