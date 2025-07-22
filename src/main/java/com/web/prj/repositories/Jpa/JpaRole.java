package com.web.prj.repositories.Jpa;

import com.web.prj.entities.Role;
import com.web.prj.enums.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRole extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByRoleId(String code);
    Optional<Role> findByRoleIdAndActive(String roleId, boolean isActive);

    @Query("select max(r.id) from Role r")
    Optional<Long> findMaxId();
}
