package com.web.prj.mappers.Impl;

import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.entities.Store;
import com.web.prj.mappers.mapper.StoreMapper;
import com.web.prj.mappers.mapstruct.MapstructStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StoreMapperImpl implements StoreMapper {
    private final MapstructStore mapper;
    @Override
    public Store toEntity(StoreRequest request) {
        return mapper.toEntity(request);
    }

    @Override
    public StoreResponse toResponse(Store store) {
        StoreResponse response = mapper.toResponse(store);

        if(store.getUser() != null){
            response.setNameManager(store.getUser().getName());
            response.setEmail(store.getUser().getEmail());
            response.setPhone(store.getUser().getPhone());
        }

        return response;
    }

    @Override
    public PageResponse<StoreResponse> toPageResponse(Page<Store> page) {
        List<StoreResponse> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PageResponse.<StoreResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
