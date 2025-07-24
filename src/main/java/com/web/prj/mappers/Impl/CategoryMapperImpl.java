package com.web.prj.mappers.Impl;

import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.Category;
import com.web.prj.mappers.mapper.CategoryMapper;
import com.web.prj.mappers.mapstruct.MapstructCategory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    private final MapstructCategory mapstructCategory;
    @Override
    public CategoryResponse toResponse(Category category) {
        return mapstructCategory.toResponse(category);
    }

    @Override
    public PageResponse<CategoryResponse> toPageResponse(Page<Category> page) {
        List<CategoryResponse> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();
        return PageResponse.<CategoryResponse>builder()
                .data(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .lastPage(page.isLast())
                .build();
    }
}
