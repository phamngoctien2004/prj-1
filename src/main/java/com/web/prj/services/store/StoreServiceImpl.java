package com.web.prj.services.store;

import com.web.prj.Helpers.SpecHelper;
import com.web.prj.dtos.request.StoreRequest;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.dtos.response.StoreResponse;
import com.web.prj.entities.Store;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.StoreMapper;
import com.web.prj.repositories.repository.StoreRepository;
import com.web.prj.repositories.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final UserRepository userRepository;
    private final StoreRepository repository;
    private final StoreMapper mapper;

    @Override
    public StoreResponse create(StoreRequest request) {
        Optional<Store> store = repository.findByStoreId(request.getStoId());

        if (store.isPresent()) {
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }

        Store newStore = mapper.toEntity(request);
        newStore.setActive(true);
        Long managerId = request.getManagerId();
        if (managerId != null && managerId != 0) {
            User manager = userRepository.findById(managerId)
                    .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
            newStore.setUser(manager);
        }

        return mapper.toResponse(repository.save(newStore));
    }

    @Override
    public StoreResponse update(StoreRequest request) {
        Store store = repository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));

        store.setName(request.getName());
        store.setAddress(request.getAddress());
        store.setActive(request.isActive());

        if(request.getManagerId() != null && request.getManagerId() != 0){
            User manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
            store.setUser(manager);
        }
        return mapper.toResponse(repository.save(store));
    }


    @Override
    public void delete(Long id) {
        Store store = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
        if(store.getOrders() != null && !store.getOrders().isEmpty()) {
            throw new AppException(ErrorCode.RECORD_UNDELETED);
        }

        repository.delete(id);
    }

    @Override
    public StoreResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOTFOUND));
    }

    @Override
    public PageResponse<StoreResponse> findAll(Pageable pageable, String filter) {
        Specification<Store> specName = SpecHelper.containField("name", filter);
        Specification<Store> specAddress = SpecHelper.containField("address", filter);
        Specification<Store> spec = specName.or(specAddress);
        return mapper.toPageResponse(repository.findAll(pageable,spec));
    }
}
