package com.example.newsapi.controller;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.service.BaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController implements BaseController<AuthorDto> {

    private final BaseService<AuthorDto> service;

    @Override
    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAll(SearchDto searchDto, Pageable pageable) {
        log.info("AuthorController getAll searchDto {} pageable {} ", searchDto, pageable);
        return ResponseEntity.ok(service.findAll(searchDto, pageable));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<AuthorDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<AuthorDto> create(@RequestBody @Valid AuthorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Override
    @CheckAccessRights
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody @Valid AuthorDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    @CheckAccessRights
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
