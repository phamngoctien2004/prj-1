package com.web.prj.mappers.mapstruct;

import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.entities.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructCategory {
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category entity);
}
