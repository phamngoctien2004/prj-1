package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.entities.Store;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructStore {
    Store toEntity(StoreRequest request);
    StoreResponse toResponse(Store store);
}
