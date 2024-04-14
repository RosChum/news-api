package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.CommentDto;
import com.example.newsapi.exception.ContentNotFoundException;
import com.example.newsapi.exception.UserNotFoundException;
import com.example.newsapi.mapper.AccountMapper;
import com.example.newsapi.mapper.CommentMapper;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.Comment;
import com.example.newsapi.repository.AccountRepository;
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
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public List<CommentDto> findAll() {
        return commentMapper.convertToListDto(commentRepository.findAll());

    }

    @Override
    public CommentDto findById(Long id) {
        return commentMapper.convertToDto(commentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Комментарий с id {0} не найден", id))));
    }

    @Transactional
    @Override
    public CommentDto create(CommentDto dto) {
        Comment comment = new Comment();
        comment.setNews(newsRepository.findById(dto.getNewsId())
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Новость с id {0} не найдена", dto.getNewsId()))));
        comment.setText(dto.getText());
        Account account =accountRepository.findById(SecurityService.getAuthenticationUserId()).orElseThrow(()-> new UserNotFoundException("Account not found"));
        comment.setAccount(account);
        comment.setTimeCreated(Instant.now());

        CommentDto commentDto = commentMapper.convertToDto(commentRepository.save(comment));
        commentDto.setShortAccountDto(accountMapper.convertToShortDto(comment.getAccount()));
        commentDto.setNewsId(comment.getNews().getId());
        return commentDto;

    }

    @Transactional
    @Override
    public CommentDto update(Long id, CommentDto dto) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new ContentNotFoundException("Comment not found"));
        comment.setTimeCreated(Instant.now());
        comment.setText(dto.getText());

        CommentDto commentDto = commentMapper.convertToDto(commentRepository.save(comment));
        commentDto.setShortAccountDto(accountMapper.convertToShortDto(comment.getAccount()));
        commentDto.setNewsId(comment.getNews().getId());
        return commentDto;
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
