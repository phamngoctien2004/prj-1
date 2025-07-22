package com.web.prj.repositories.Jpa;

import com.web.prj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUser extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    @Query("select MAX(u.id) from User u")
    Optional<Long> findMaxId();

//    Page<User> findAll(Pageable page);
}
