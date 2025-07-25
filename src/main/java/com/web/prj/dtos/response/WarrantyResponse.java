package com.web.prj.dtos.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Data
public class WarrantyResponse {
    private Long id;
    private String warrantyId;
    private String name;
    private String description;
    private int monthWarranty;
    private boolean active;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
