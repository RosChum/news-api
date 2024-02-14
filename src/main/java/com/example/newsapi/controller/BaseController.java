package com.example.newsapi.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController <T>{

    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> findById(Long id);

    ResponseEntity<T> create(T dto);

    ResponseEntity<T> update(Long id, T dto);

    ResponseEntity<Void> deleteById(Long id);
}
