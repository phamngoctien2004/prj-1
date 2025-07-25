package com.web.prj.services.warranty;

import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.request.WarrantyRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.dtos.response.WarrantyResponse;
import org.springframework.data.domain.Pageable;

public interface WarrantyService {
    WarrantyResponse create(WarrantyRequest request);
    WarrantyResponse update(WarrantyRequest store);
    void delete(Long id);
    WarrantyResponse findById(Long id);
    PageResponse<WarrantyResponse> findAll(Pageable pageable, String filter, int month);
}
