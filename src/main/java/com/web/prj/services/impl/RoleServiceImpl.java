package com.web.prj.services.impl;

import com.web.prj.entities.Role;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.repositories.repository.RoleRepository;
import com.web.prj.services.cores.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByCode(String code){
        return repository.findByRoleId(code)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

}
