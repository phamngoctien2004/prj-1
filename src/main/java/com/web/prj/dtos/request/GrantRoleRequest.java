package com.web.prj.dtos.request;

import lombok.Data;

@Data
public class GrantRoleRequest {
    private Long roleId;
    private Long userId;
}
