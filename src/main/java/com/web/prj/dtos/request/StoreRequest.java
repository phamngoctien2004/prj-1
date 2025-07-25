package com.web.prj.dtos.request;

import com.web.prj.annotations.Code;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreRequest {
    private Long id;

    @NotBlank
    @Code
    private String stoId;

    private Long managerId;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    private boolean active = true;
}
