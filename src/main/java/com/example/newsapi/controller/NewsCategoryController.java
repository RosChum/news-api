package com.example.newsapi.controller;

import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.model.NewsСategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class NewsCategoryController implements BaseController<NewsСategory>{
    @Override
    public ResponseEntity<Page<NewsСategory>> getAll(SearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<NewsСategory> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<NewsСategory> create(NewsСategory dto) {
        return null;
    }

    @Override
    public ResponseEntity<NewsСategory> update(Long id, NewsСategory dto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        return null;
    }
}
