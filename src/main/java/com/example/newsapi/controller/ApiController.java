package com.example.newsapi.controller;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiController {


    private final ApiService apiService;

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        return ResponseEntity.ok().body(apiService.getAllNews());
    }


    @PostMapping("/add")
    public ResponseEntity<List<NewsDto>> createNews(@RequestBody NewsDto newsDto) {
        log.info(" createNews {}", newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiService.createNews(newsDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> searchNews(@RequestBody SearchDto searchDto) {
        log.info(" searchNews {}", searchDto);
        return ResponseEntity.ok(apiService.getSearch(searchDto));
    }


}
