package com.web.prj.mappers.mapper;

import com.web.prj.dtos.request.WarrantyRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.WarrantyResponse;
import com.web.prj.entities.Warranty;
import org.springframework.data.domain.Page;

public interface WarrantyMapper {
    Warranty toEntity(WarrantyRequest dto);
    WarrantyResponse toResponse(Warranty entity);

    PageResponse<WarrantyResponse> toPageResponse(Page<Warranty> page);
}
