package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.News;
import com.example.newsapi.repository.AuthorRepository;
import com.example.newsapi.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NewsService implements BaseService<NewsDto> {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;


    @Override
    public Page<NewsDto> findAll(SearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public NewsDto findById(Long id) {
        return newsMapper.convertToDto(newsRepository.findById(id)
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Новость с id {0} не найдена", id))));
    }

    @Override
    public NewsDto create(NewsDto dto) {
        NewsDto newsDto = newsMapper.convertToDto(newsRepository.save(newsMapper.convertToEntity(dto)));
        newsDto.setShortAuthorDto(authorMapper.convertToShortDto(authorRepository.findById(dto.getShortAuthorDto().getId()).orElseThrow()));
        return newsDto;
    }

    @CheckAccessRights
    @Transactional
    @Override
    public NewsDto update(Long id, NewsDto dto) {
        News existNews = newsRepository.findById(id)
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Новость с id {0} для обновления  не найдена", id)));
        existNews.setTitle(dto.getTitle());
        existNews.setDescription(dto.getDescription());
        existNews.setUpdateTime(Instant.now());
        NewsDto resultDto = newsMapper.convertToDto(newsRepository.save(existNews));
        resultDto.setShortAuthorDto(authorMapper.convertToShortDto(existNews.getAuthor()));
        return resultDto;
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }


}
