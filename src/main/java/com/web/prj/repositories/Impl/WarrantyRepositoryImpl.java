package com.web.prj.repositories.Impl;

import com.web.prj.entities.Warranty;
import com.web.prj.repositories.Jpa.JpaWarranty;
import com.web.prj.repositories.repository.WarrantyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class WarrantyRepositoryImpl implements WarrantyRepository {
    private final JpaWarranty jpaWarranty;

    @Override
    public Optional<Warranty> findById(Long id) {
        return jpaWarranty.findById(id);
    }

    @Override
    public void delete(Long id) {
        jpaWarranty.deleteById(id);
    }

    @Override
    public Warranty save(Warranty warranty) {
        return jpaWarranty.save(warranty);
    }

    @Override
    public Page<Warranty> findALl(Pageable pageable, Specification<Warranty> spec) {
        return jpaWarranty.findAll(spec, pageable);
    }
}
