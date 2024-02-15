package com.example.newsapi.service;

import com.example.newsapi.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {

    Page<T> findAll(SearchDto searchDto, Pageable pageable);

    T findById(Long id);

    T create(T dto);

    T update(Long id, T dto);

    void deleteById(Long id);


}
