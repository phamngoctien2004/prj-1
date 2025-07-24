package com.web.prj.mappers.mapper;

import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.Category;
import org.springframework.data.domain.Page;

public interface CategoryMapper {
    CategoryResponse toResponse(Category category);

    PageResponse<CategoryResponse> toPageResponse(Page<Category> page);
}
