package com.web.prj.services.category;

import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.dtos.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(CategoryRequest request);
    void delete(Long id);

    PageResponse<CategoryResponse> findAll(Pageable pageable, String filter);
    CategoryResponse getDetail(Long id);
}
