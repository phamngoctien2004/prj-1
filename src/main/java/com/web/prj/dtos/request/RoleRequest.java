package com.web.prj.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.prj.enums.ROLE;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequest {
    private Long id;
    @NotBlank(message = "không được trống")
    private String name;

    @NotBlank(message = "không được trống")
    private String roleId;
    private Boolean active = true;
    private List<Long> permissionIds;
}
