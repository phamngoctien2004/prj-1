package com.web.prj.repositories.repository;

import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long id);
    Optional<Category> findByCategoryId(String categoryId);
    Page<Category> findAll(Pageable pageable, Specification<Category> spec);
    Category save(Category category);
    void delete(Long id);
}
