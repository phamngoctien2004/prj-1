package com.web.prj.dtos.response;

import com.web.prj.entities.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoreResponse {
    private Long id;
    private String stoId;
    private String name;
    private String address;
    private String nameManager;
    private String email;
    private String phone;
    private boolean active;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
