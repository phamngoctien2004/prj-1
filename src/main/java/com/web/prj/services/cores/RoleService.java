package com.web.prj.services.cores;

import com.web.prj.entities.Role;

public interface RoleService {
    Role findByCode(String code);
}
