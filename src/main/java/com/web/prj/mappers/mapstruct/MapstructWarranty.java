package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.request.WarrantyRequest;
import com.web.prj.dtos.response.WarrantyResponse;
import com.web.prj.entities.Warranty;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructWarranty {
    Warranty toEntity(WarrantyRequest dto);
    WarrantyResponse toResponse(Warranty entity);
}
