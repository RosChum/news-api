package com.example.newsapi.service;

import java.util.List;

public interface BaseService<T> {

    List<T> getAll();

    T findById(Long id);

    T create(T dto);

    T update(Long id, T dto);

    void deleteById(Long id);


}
