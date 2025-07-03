package com.web.prj.services.interfaces;

import java.util.List;

public interface ICrudService<T>{
    List<T> findAll();

    void save(T data);

    void delete(Long id);
}
