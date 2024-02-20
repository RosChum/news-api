package com.example.newsapi.service;

import com.example.newsapi.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T> {

    default Page<T> findAll(SearchDto searchDto, Pageable pageable) {
        return null;
    }

    T findById(Long id);

    T create(T dto);

    T update(Long id, T dto);

    void deleteById(Long id);


}
