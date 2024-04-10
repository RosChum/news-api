package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.ShortNewsDto;
import com.example.newsapi.model.News;
import com.example.newsapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public News convertToEntity(NewsDto dto) {
        if (dto == null) return null;

        News news = new News();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setCreateTime(Instant.now());
        news.setUpdateTime(Instant.now());
        return news;

    }

    @Override
    public List<NewsDto> convertToListDto(List<News> newsList) {
        if (newsList == null) return null;

        List<NewsDto> newsDtoList = new ArrayList<>();

        newsList.forEach(news -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setId(news.getId());
            newsDto.setShortAccountDto(accountMapper.convertToShortDto(news.getAccount()));
            newsDto.setTitle(news.getTitle());
            newsDto.setDescription(news.getDescription());
            newsDto.setCreateTime(news.getCreateTime());
            newsDto.setUpdateTime(news.getUpdateTime());
            newsDtoList.add(newsDto);

        });

        return newsDtoList;
    }

    @Override
    public List<ShortNewsDto> convertToShortDto(List<News> newsList) {
        if (newsList == null) return null;

        List<ShortNewsDto> shortNewsDtos = new ArrayList<>();
        newsList.forEach(news -> {
            ShortNewsDto newsDto = new ShortNewsDto();
            newsDto.setId(news.getId());
            newsDto.setTitle(news.getTitle());
            newsDto.setDescription(news.getDescription());
            newsDto.setCreateTime(news.getCreateTime());
            newsDto.setUpdateTime(news.getUpdateTime());
            shortNewsDtos.add(newsDto);

        });
        return shortNewsDtos;
    }
}
