package com.web.prj.repositories.repository;

import com.web.prj.entities.Warranty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface WarrantyRepository {
    Optional<Warranty> findById(Long id);

    void delete(Long id);
    Warranty save(Warranty warranty);

    Page<Warranty> findALl(Pageable pageable, Specification<Warranty> spec);
}
