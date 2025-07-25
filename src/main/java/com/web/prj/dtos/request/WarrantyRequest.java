package com.web.prj.dtos.request;

import com.web.prj.annotations.Code;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarrantyRequest {
    private Long id;

//    uuid
    private String warrantyId;

    @NotBlank
    private String name;

    private String description;

    @Min(value=1)
    private int monthWarranty;
    private boolean active = true;
}
