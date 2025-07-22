package com.web.prj.dtos.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRequest {
    private Long id;

    private String name;

    private String module;
    private String action;

    private boolean active = true;
}
