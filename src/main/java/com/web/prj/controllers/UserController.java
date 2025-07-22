package com.web.prj.controllers;

import com.web.prj.dtos.dto.UserDTO;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.UserMapper;
import com.web.prj.repositories.repository.UserRepository;
import com.web.prj.services.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> myInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO userDTO = userMapper.toDto(
                userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam("filter") Optional<String> filter) {
        Page<User> page = userService.findAllByPageAndFilter(pageable, filter);
        ApiResponse<PageResponse<UserDTO>> response = ApiResponse.<PageResponse<UserDTO>>builder()
                .code("200")
                .message("Lấy dữ liệu thành công")
                .data(userMapper.toPageResponse(page))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userMapper.toDto(userService.createUser(userDTO));
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("201")
                .message("Tạo người dùng thành công")
                .data(createdUser)
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        UserDTO user = userMapper.toDto(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .code("200")
                .data(user)
                .message("Lấy người dùng thành công")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
        UserDTO updatedUser = userMapper.toDto(userService.updateUser(userDTO));
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
