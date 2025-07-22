package com.web.prj.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.prj.enums.ROLE;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequest {
    private Long id;
    private String name;
    private String roleId;
    private Boolean active = true;
    private List<Long> permissionIds;
}
