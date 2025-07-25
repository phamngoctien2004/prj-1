package com.web.prj.services.warranty;

import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.WarrantyRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.WarrantyResponse;
import com.web.prj.entities.Warranty;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.WarrantyMapper;
import com.web.prj.repositories.repository.WarrantyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WarrantyServiceImpl implements WarrantyService {
    private final WarrantyRepository repository;
    private final WarrantyMapper mapper;
    @Override
    public WarrantyResponse create(WarrantyRequest request) {
        String warrantyId = UUID.randomUUID().toString();
        Warranty warranty = Warranty.builder()
                .warrantyId(warrantyId)
                .name(request.getName())
                .description(request.getDescription())
                .monthWarranty(request.getMonthWarranty())
                .active(true)
                .build();
        return mapper.toResponse(repository.save(warranty));
    }

    @Override
    public WarrantyResponse update(WarrantyRequest store) {
        Warranty warranty = repository.findById(store.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
        warranty.setName(store.getName());
        warranty.setDescription(store.getDescription());
        warranty.setMonthWarranty(store.getMonthWarranty());
        warranty.setActive(store.isActive());
        return mapper.toResponse(repository.save(warranty));
    }

    @Override
    public void delete(Long id) {
        Warranty warranty = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
        if(warranty.getProducts() != null && !warranty.getProducts().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }
        repository.delete(id);
    }

    @Override
    public WarrantyResponse findById(Long id) {
        Warranty warranty = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
        if(warranty.getProducts() != null && !warranty.getProducts().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }
        return mapper.toResponse(warranty);
    }

    @Override
    public PageResponse<WarrantyResponse> findAll(Pageable pageable, String filter, int month) {
        Specification<Warranty> specName = SpecHelper.containField("name", filter);
        Specification<Warranty> specDescription = SpecHelper.containField("description", filter);
        Specification<Warranty> spec = specName.or(specDescription);
        if(month > 0){
            Specification<Warranty> specMonth = SpecHelper.equal("monthWarranty", month);
            spec.and(specMonth);
        }
        Page<Warranty> page = repository.findALl(pageable, spec);
        return mapper.toPageResponse(page);
    }
}
