package com.web.prj.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse {
    private Long id;
    private String name;
    private String module;
    private String action;
    private boolean active;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
//    private String description;
}
