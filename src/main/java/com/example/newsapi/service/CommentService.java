package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.CommentDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.CommentMapper;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.Comment;
import com.example.newsapi.repository.CommentRepository;
import com.example.newsapi.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements BaseService<CommentDto> {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public List<CommentDto> findAll() {
        return commentMapper.convertToListDto(commentRepository.findAll());

    }

    @Override
    public CommentDto findById(Long id) {
        return commentMapper.convertToDto(commentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("комментарий с id {0} не найден", id))));
    }

    @Transactional
    @Override
    public CommentDto create(CommentDto dto) {
        Comment comment = new Comment();
        comment.setNews(newsRepository.findById(dto.getNewsId())
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Новость с id {0} не найден для комментария не найдена", dto.getNewsId()))));
        comment.setText(dto.getText());
        Author author = authorMapper.convertToEntity(authorService.findById(dto.getShortAuthorDto().getId()));
        if (author.getId() == null) {
            authorService.create(authorMapper.shortAuthorDtoConvertToDto(dto.getShortAuthorDto()));
        }
        comment.setAuthor(author);
        comment.setTimeCreated(Instant.now());
        CommentDto commentDto = commentMapper.convertToDto(commentRepository.save(comment));
        commentDto.setShortAuthorDto(authorMapper.convertToShortDto(comment.getAuthor()));
        commentDto.setNewsId(comment.getNews().getId());
        return commentDto;

    }

    @CheckAccessRights
    @Transactional
    @Override
    public CommentDto update(Long id, CommentDto dto) {
        Comment comment = commentMapper.convertToEntity(findById(id));
        comment.setAuthor(authorMapper.convertToEntity(authorService.findById(dto.getShortAuthorDto().getId())));
        comment.setTimeCreated(Instant.now());
        comment.setText(dto.getText());
        comment.setNews(newsRepository.findById(dto.getNewsId())
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Новость с id {0} не найден для комментария не найдена", dto.getNewsId()))));
        CommentDto commentDto = commentMapper.convertToDto(commentRepository.save(comment));
        commentDto.setShortAuthorDto(authorMapper.convertToShortDto(comment.getAuthor()));
        commentDto.setNewsId(comment.getNews().getId());
        return commentDto;
    }

    @CheckAccessRights
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
