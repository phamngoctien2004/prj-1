package com.web.prj.mappers.mapper;

import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.entities.Store;
import org.springframework.data.domain.Page;

public interface StoreMapper {
    Store toEntity(StoreRequest request);
    StoreResponse toResponse(Store store);
    PageResponse<StoreResponse> toPageResponse(Page<Store> page);

}
