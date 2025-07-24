package com.web.prj.services.category;

import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.CategoryRequest;
import com.web.prj.dtos.response.CategoryResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.Category;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.CategoryMapper;
import com.web.prj.repositories.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    @Override
    public CategoryResponse create(CategoryRequest request) {
        String catId = request.getCatId().toLowerCase();
        if(repository.findByCategoryId(catId).isPresent()){
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .catId(catId)
                .build();

        return mapper.toResponse(repository.save(category));
    }

    @Override
    public CategoryResponse update(CategoryRequest request) {
        Category category = repository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setActive(request.isActive());
        return mapper.toResponse(repository.save(category));
    }

    @Override
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        if(category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }
        repository.delete(id);
    }

    @Override
    public PageResponse<CategoryResponse> findAll(Pageable pageable, String filter) {
        Specification<Category> specName = SpecHelper.containField("name", filter);
        Specification<Category> specCatId = SpecHelper.containField("catId", filter);
        Specification<Category> spec = specName.or(specCatId);
        Page<Category> page = repository.findAll(pageable, spec);
        return mapper.toPageResponse(page);
    }

    @Override
    public CategoryResponse getDetail(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
    }
}
