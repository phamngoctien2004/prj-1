package com.web.prj.repositories.repository;

import com.web.prj.entities.Role;
import org.springframework.data.domain.Page;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleId(String code);

}
