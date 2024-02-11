package com.example.newsapi.service;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.News;
import com.example.newsapi.repository.AuthorRepository;
import com.example.newsapi.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;
    private final AuthorMapper authorMapper;


    public List<NewsDto> getAllNews() {


        return newsRepository.findAll().stream().map(newsMapper::convertToDto).collect(Collectors.toList());
    }


    public List<NewsDto> createNews(NewsDto newsDto) {
        Map<Author, News> authorNewsMap = newsMapper.convertToEntity(newsDto);
        for (var authorAndNews : authorNewsMap.entrySet()) {
            authorRepository.save(authorAndNews.getKey());
            newsRepository.save(authorAndNews.getValue());
        }

        return getAllNews();

    }
}
