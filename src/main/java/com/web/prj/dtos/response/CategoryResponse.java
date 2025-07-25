package com.web.prj.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private String id;
    private String catId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
}
