package com.web.prj.dtos.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRequest {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank(message = "không được trống")
    private String module;

    @NotBlank(message = "không được trống")
    private String action;

    private boolean active = true;
}
