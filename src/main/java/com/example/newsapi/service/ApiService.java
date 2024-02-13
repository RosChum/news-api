package com.example.newsapi.service;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.Author_;
import com.example.newsapi.model.News;
import com.example.newsapi.model.News_;
import com.example.newsapi.repository.AuthorRepository;
import com.example.newsapi.repository.NewsRepository;
import com.example.newsapi.repository.NewsSpecification;
import com.example.newsapi.util.ZonedDateTimeConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.newsapi.repository.NewsSpecification.*;

@Service
@RequiredArgsConstructor
public class ApiService {

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
                .and(getSpecificationBetween(News_.updateTime, !searchDto.getTimeFrom().isEmpty() ? ZonedDateTimeConvertor.convertToZonedDateTime(searchDto.getTimeFrom()) : null
                        , !searchDto.getTimeFrom().isEmpty() ? ZonedDateTimeConvertor.convertToZonedDateTime(searchDto.getTimeTo()) : null))
                        .and(joinSpecificationAuthors(Author_.FIRST_NAME, !searchDto.getFirstNameAuthor().isEmpty()? searchDto.getFirstNameAuthor() : null))
                        .or((joinSpecificationAuthors(Author_.LAST_NAME, !searchDto.getLastNameAuthor().isEmpty()? searchDto.getLastNameAuthor() : null))));


        return newsMapper.convertToListDto(resultSearch);
    }


}
