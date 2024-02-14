package com.example.newsapi.controller;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.service.BaseService;
import com.example.newsapi.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController implements BaseController<NewsDto> {


    private final NewsService newsService;

    @GetMapping
    @Override
    public ResponseEntity<List<NewsDto>> getAll() {
        return ResponseEntity.ok().body(newsService.getAll());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<NewsDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(newsService.findById(id));
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto dto) {
        log.info(" create " + dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(dto));
    }

    @PutMapping("/update/{newsId}/{accountId}")
    @Override
    public ResponseEntity<NewsDto> update(@PathVariable(value = "newsId") Long id, @RequestBody NewsDto dto) {
        log.info(" create " + id + " " + dto);
        return ResponseEntity.ok(newsService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> searchNews(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(newsService.getSearch(searchDto));
    }

}



