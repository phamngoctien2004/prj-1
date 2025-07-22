package com.web.prj.dtos.response;

import com.web.prj.enums.ROLE;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleResponse {
    private Long id;
    private String roleId;
    private String name;
    private Boolean active;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    private List<PermissionResponse> permissions;
}
