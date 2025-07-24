package com.web.prj.repositories.Impl;

import com.web.prj.entities.Category;
import com.web.prj.repositories.Jpa.JpaCategory;
import com.web.prj.repositories.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JpaCategory jpaCategory;

    @Override
    public Optional<Category> findById(Long id) {
        return jpaCategory.findById(id);
    }

    @Override
    public Optional<Category> findByCategoryId(String categoryId) {
        return jpaCategory.findByCatId(categoryId);
    }

    @Override
    public Page<Category> findAll(Pageable pageable, Specification<Category> spec) {
        return jpaCategory.findAll(spec, pageable);
    }

    @Override
    public Category save(Category category) {
        return jpaCategory.save(category);
    }

    @Override
    public void delete(Long id) {
        jpaCategory.deleteById(id);
    }
}
