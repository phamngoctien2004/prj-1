package com.web.prj.services.interfaces;

import java.util.List;

public interface ICrudService<T, R>{
    List<T> findAll();

    void saveDTO(T data);
    void save(R data);

    void delete(Long id);
}
