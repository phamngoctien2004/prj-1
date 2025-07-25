package com.web.prj.controllers;

import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;
    @GetMapping
    ResponseEntity<?> findAll(Pageable pageable, Optional<String> filter) {
        var response = service.findAll(pageable, filter.orElse(""));
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Categories retrieved successfully")
        );
    }
    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        var response = service.getDetail(id);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category retrieved successfully")
        );
    }
    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody @Valid CategoryRequest request) {
        var response = service.create(request);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category created successfully")
        );
    }

    @PutMapping
    ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryRequest request) {
        var response = service.update(request);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category updated successfully")
        );
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(null, "Category deleted successfully")
        );
    }

}
