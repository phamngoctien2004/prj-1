package com.web.prj.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    private Long id;

    @NotBlank
    private String catId;

    @NotBlank
    private String name;
    private String description;
    private boolean active;
}
