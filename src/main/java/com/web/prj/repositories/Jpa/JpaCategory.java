package com.web.prj.repositories.Jpa;

import com.web.prj.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaCategory extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByCatId(String catId);
}
