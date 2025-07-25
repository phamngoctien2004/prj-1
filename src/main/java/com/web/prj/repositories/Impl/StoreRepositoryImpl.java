package com.web.prj.repositories.Impl;

import com.web.prj.entities.Store;
import com.web.prj.repositories.Jpa.JpaStore;
import com.web.prj.repositories.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {
    private final JpaStore jpaStore;
    @Override
    public Optional<Store> findById(Long id) {
        return jpaStore.findById(id);
    }

    @Override
    public Optional<Store> findByStoreId(String storeId) {
        return jpaStore.findByStoId(storeId);
    }

    @Override
    public Page<Store> findAll(Pageable pageable, Specification<Store> spec) {
        return jpaStore.findAll(spec, pageable);
    }

    @Override
    public Store save(Store store) {
        return jpaStore.save(store);
    }

    @Override
    public void delete(Long id) {
        jpaStore.deleteById(id);
    }
}
