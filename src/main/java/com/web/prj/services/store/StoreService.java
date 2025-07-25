package com.web.prj.services.store;

import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.entities.Store;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    StoreResponse create(StoreRequest request);
    StoreResponse update(StoreRequest store);
    void delete(Long id);
    StoreResponse findById(Long id);
    PageResponse<StoreResponse> findAll(Pageable pageable, String filter);
}
