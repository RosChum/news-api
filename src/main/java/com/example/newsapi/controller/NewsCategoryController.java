package com.example.newsapi.controller;

import com.example.newsapi.dto.NewsCategoryDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.service.NewsCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<Page<NewsCategoryDto>> getAll(SearchDto searchDto, Pageable pageable) {
        return ResponseEntity.ok(newsCategoryService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<NewsCategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsCategoryService.findById(id));
    }


    @PostMapping("/add")
    public ResponseEntity<List<NewsCategoryDto>> create(@RequestBody @Valid List<NewsCategoryDto> dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsCategoryService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<NewsCategoryDto> update(@RequestBody @Valid NewsCategoryDto dto) {
        return ResponseEntity.ok().body(newsCategoryService.update(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
