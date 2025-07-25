package com.web.prj.controllers;

import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.services.store.StoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/store")
@AllArgsConstructor
public class StoreController {
    private final StoreService service;
    @GetMapping
    ResponseEntity<?> findAll(Pageable pageable, Optional<String> filter) {
        var response = service.findAll(pageable, filter.orElse(""));
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Categories retrieved successfully")
        );
    }
    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category retrieved successfully")
        );
    }
    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid StoreRequest request) {
        var response = service.create(request);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category created successfully")
        );
    }

    @PutMapping
    ResponseEntity<?> update(@RequestBody @Valid StoreRequest request) {
        var response = service.update(request);
        return ResponseEntity.ok(
                new ApiResponse<>(response, "Category updated successfully")
        );
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(null, "Category deleted successfully")
        );
    }
}
