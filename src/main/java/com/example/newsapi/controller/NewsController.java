package com.example.newsapi.controller;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
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
public class NewsController {


    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        return ResponseEntity.ok().body(newsService.getAllNews());
    }


    @PostMapping("/add")
    public ResponseEntity<List<NewsDto>> createNews(@RequestBody NewsDto newsDto) {
        log.info(" createNews {}", newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.createNews(newsDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> searchNews(@RequestBody SearchDto searchDto) {
        log.info(" searchNews {}", searchDto);
        return ResponseEntity.ok(newsService.getSearch(searchDto));
    }

    @PutMapping("/update/{id}/{accountId}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable(value = "id") Long newsId, @PathVariable Long accountId, @RequestBody NewsDto newsDto) {
        return ResponseEntity.ok(newsService.updateNews(newsId, newsDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }
}



