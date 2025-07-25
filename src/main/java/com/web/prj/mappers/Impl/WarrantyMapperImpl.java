package com.web.prj.mappers.Impl;

import com.web.prj.dtos.request.WarrantyRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.UserResponse;
import com.web.prj.dtos.response.WarrantyResponse;
import com.web.prj.entities.Warranty;
import com.web.prj.mappers.mapper.WarrantyMapper;
import com.web.prj.mappers.mapstruct.MapstructWarranty;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WarrantyMapperImpl implements WarrantyMapper {
    private final MapstructWarranty mapstructWarranty;
    @Override
    public Warranty toEntity(WarrantyRequest dto) {
        return mapstructWarranty.toEntity(dto);
    }

    @Override
    public WarrantyResponse toResponse(Warranty entity) {
        return mapstructWarranty.toResponse(entity);
    }

    @Override
    public PageResponse<WarrantyResponse> toPageResponse(Page<Warranty> page) {
        List<WarrantyResponse> content = page.getContent().stream()
                .map(mapstructWarranty::toResponse)
                .toList();
        return PageResponse.<WarrantyResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
