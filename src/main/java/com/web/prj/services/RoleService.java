package com.web.prj.services;

import com.web.prj.dtos.RoleDTO;
import com.web.prj.entities.Role;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.repositories.RoleRepository;
import com.web.prj.services.interfaces.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements ICrudService<RoleDTO, Role> {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> findAll() {
        return null;
    }

    @Override
    public void saveDTO(RoleDTO data) {

    }

    @Override
    public void save(Role data) {

    }

    @Override
    public void delete(Long id) {

    }

    public Role findByCode(String code){
        return roleRepository.findByRoleId(code)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

}
