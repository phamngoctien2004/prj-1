package com.web.prj.repositories.Jpa;

import com.web.prj.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaStore extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    Optional<Store> findByStoId(String storeId);
}
