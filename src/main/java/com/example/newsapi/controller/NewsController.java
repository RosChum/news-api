package com.example.newsapi.controller;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController implements BaseController<NewsDto> {


    private final NewsService newsService;

    @GetMapping
    @Override
    public ResponseEntity<Page<NewsDto>> getAll(SearchDto searchDto, Pageable pageable) {
        log.info("NewsController getAll SearchDto " + searchDto);
        return ResponseEntity.ok().body(newsService.findAll(searchDto, pageable));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<NewsDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(newsService.findById(id));
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<NewsDto> create(@RequestBody @Valid NewsDto dto) {
        log.info("create " + dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(dto));
    }

    @PutMapping("/update/{id}/{accountId}")
    @Override
    public ResponseEntity<NewsDto> update(@PathVariable Long id, @RequestBody @Valid NewsDto dto) {
        log.info("update " + id + " " + dto);
        return ResponseEntity.ok(newsService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}/{accountId}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}



