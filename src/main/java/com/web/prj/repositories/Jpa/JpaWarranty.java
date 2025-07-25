package com.web.prj.repositories.Jpa;

import com.web.prj.entities.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaWarranty extends JpaRepository<Warranty, Long>, JpaSpecificationExecutor<Warranty> {
}
