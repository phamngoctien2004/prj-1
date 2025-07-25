package com.web.prj.repositories.repository;

import com.web.prj.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface StoreRepository {
    Optional<Store> findById(Long id);
    Optional<Store> findByStoreId(String storeId);
    Page<Store> findAll(Pageable pageable, Specification<Store> spec);

    Store save(Store store);

    void delete(Long id);
}


