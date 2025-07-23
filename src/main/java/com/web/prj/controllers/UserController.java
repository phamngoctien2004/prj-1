package com.web.prj.controllers;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.request.GrantRoleRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.services.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "Role", description = "Quản lý các quyền trong hệ thống")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> myInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO userDTO = userService.getUserDetail(email);
        return ResponseEntity.ok(
                new ApiResponse<>(userDTO, "Lấy thông tin người dùng thành công")
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam("filter") Optional<String> filter) {

        ApiResponse<PageResponse<UserDTO>> response = ApiResponse.<PageResponse<UserDTO>>builder()
                .code("200")
                .message("Lấy dữ liệu thành công")
                .data(userService.findAllByPageAndFilter(pageable, filter))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user_create')")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("201")
                .message("Tạo người dùng thành công")
                .data(createdUser)
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> getEmailDetail(@PathVariable String email){
        UserDTO user = userService.getUserDetail(email);
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("200")
                .data(user)
                .message("Lấy người dùng thành công")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(userDTO);
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("200")
                .message("Cập nhật người dùng thành công")
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code("200")
                .message("Xoá người dùng thành công")
                .data("1")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/grant-role")
    public ResponseEntity<?> grantRole(@RequestBody GrantRoleRequest request) {
        UserDTO updatedUser = userService.grantRole(request.getRoleId(), request.getUserId());
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("200")
                .message("Cấp quyền thành công")
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }
//    @GetMapping("/export")
//    public ResponseEntity<?> export() {
//        List<UserDTO> users = userService.findAll();
//
//        String[] cols = {
//                "ID",
//                "Email",
//        };
//
//        ExportMapper<UserDTO> mapper = (index, user) -> {
//            return switch (index){
//                case 0 -> user.getId();
//                case 1 -> user.getEmail();
//                case 2 -> user.getName();
//                default -> null;
//            };
//        };
//        ByteArrayInputStream in = ExportHelper.<UserDTO>exportExcel(users, mapper,cols);
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=users.xlsx");
//
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(in));
//    }
//
//    @PostMapping("/import")
//    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file){
//        ImportHelper.<UserDTO>importExcel(file, (row) -> {
//            long id = (long) row.getCell(0).getNumericCellValue();
//            String email = row.getCell(1).getStringCellValue();
//
//            return UserDTO.builder()
//                    .id(id)
//                    .email(row.getCell(1).getStringCellValue())
//                    .build();
//        });
//        return ResponseEntity.ok("success");
//    }
}
