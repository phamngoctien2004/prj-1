package com.web.prj.services.helpers;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserServiceHelper {
    Long findAccountIdLatest();

    User findById(Long id);
}
