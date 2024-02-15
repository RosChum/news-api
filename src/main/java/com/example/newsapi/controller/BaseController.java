package com.example.newsapi.controller;

import com.example.newsapi.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController <T>{

    ResponseEntity<Page<T>> getAll(SearchDto searchDto, Pageable pageable);

    ResponseEntity<T> findById(Long id);

    ResponseEntity<T> create(T dto);

    ResponseEntity<T> update(Long id, T dto);

    ResponseEntity<Void> deleteById(Long id);
}
