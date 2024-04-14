package com.example.newsapi.controller;

import com.example.newsapi.annotation.AccessType;
import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.CommentDto;
import com.example.newsapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController implements BaseController<CommentDto> {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<CommentDto> create(@RequestBody @Valid CommentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(dto));
    }

    @PutMapping("/update/{id}")
    @Override
    @CheckAccessRights(checkAccessType = AccessType.Comment)
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody @Valid CommentDto dto) {
        return ResponseEntity.ok().body(commentService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @CheckAccessRights(checkAccessType = AccessType.Comment)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
