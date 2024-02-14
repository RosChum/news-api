package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Author_;
import com.example.newsapi.model.News;
import com.example.newsapi.model.News_;
import com.example.newsapi.repository.AuthorRepository;
import com.example.newsapi.repository.NewsRepository;
import com.example.newsapi.repository.NewsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

import static com.example.newsapi.repository.NewsSpecification.getSpecificationBetween;
import static com.example.newsapi.repository.NewsSpecification.joinSpecificationAuthors;

@Service
@RequiredArgsConstructor
public class NewsService implements BaseService<NewsDto> {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;


    @Override
    public List<NewsDto> getAll() {
        return newsMapper.convertToListDto(newsRepository.findAll());
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

    public List<NewsDto> getSearch(SearchDto searchDto) {
        List<News> resultSearch = newsRepository.findAll(NewsSpecification.getSpecificationLike(News_.title, searchDto.getTitleNews())
                .and(NewsSpecification.getSpecificationLike(News_.description, searchDto.getDescriptionNews()))
                .and(getSpecificationBetween(News_.updateTime, !searchDto.getTimeFrom().isEmpty() ? Instant.parse(searchDto.getTimeFrom()) : null
                        , !searchDto.getTimeTo().isEmpty() ? Instant.parse(searchDto.getTimeTo()) : null))
                .and(joinSpecificationAuthors(Author_.FIRST_NAME, !searchDto.getFirstNameAuthor().isEmpty() ? searchDto.getFirstNameAuthor() : null))
                .or((joinSpecificationAuthors(Author_.LAST_NAME, !searchDto.getLastNameAuthor().isEmpty() ? searchDto.getLastNameAuthor() : null))));
        return newsMapper.convertToListDto(resultSearch);
    }

}
