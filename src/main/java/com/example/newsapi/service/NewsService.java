package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Author;
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
import java.util.Map;

import static com.example.newsapi.repository.NewsSpecification.getSpecificationBetween;
import static com.example.newsapi.repository.NewsSpecification.joinSpecificationAuthors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;
    private final AuthorMapper authorMapper;


    public List<NewsDto> getAllNews() {
        return newsMapper.convertToListDto(newsRepository.findAll());
    }


    public List<NewsDto> createNews(NewsDto newsDto) {
        Map<Author, News> authorNewsMap = newsMapper.convertToEntity(newsDto);
        for (var authorAndNews : authorNewsMap.entrySet()) {
            authorRepository.save(authorAndNews.getKey());
            newsRepository.save(authorAndNews.getValue());
        }

        return getAllNews();

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

    @CheckAccessRights
    @Transactional
    public NewsDto updateNews(Long newsId,Long authorId, NewsDto newsDto) {
        News existNews = newsRepository.findById(newsId).orElseThrow(() -> new ContentNotFound(MessageFormat.format("Новость с id {0} для обновления  не найдена", newsId)));
        existNews.setTitle(newsDto.getTitle());
        existNews.setDescription(newsDto.getDescription());
        existNews.setUpdateTime(Instant.now());
        NewsDto resultDto = newsMapper.convertToDto(newsRepository.save(existNews));
        resultDto.setAuthorDto(authorMapper.convertToDto(existNews.getAuthor()));
        return resultDto;

    }
}
