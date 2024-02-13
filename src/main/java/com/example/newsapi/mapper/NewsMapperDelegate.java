package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.News;
import com.example.newsapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;


    @Override
    public HashMap<Author, News> convertToEntity(NewsDto dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setCreateTime(Instant.now());
        news.setUpdateTime(Instant.now());

        Author author = authorRepository.findByFirstNameAndLastName(dto.getAuthorDto().getFirstName(), dto.getAuthorDto().getLastName())
                .orElse(authorMapper.convertToEntity(dto.getAuthorDto()));

        if (author.getNews() == null) {
            ArrayList<News> newsArrayList = new ArrayList<>();
            newsArrayList.add(news);
            author.setNews(newsArrayList);
        } else {
            author.getNews().add(news);
        }
        news.setAuthor(author);
        HashMap<Author, News> result = new HashMap<>();
        result.put(author, news);
        return result;

    }

    @Override
    public List<NewsDto> convertToListDto(List<News> newsList) {
        if (newsList == null) return null;

        List<NewsDto> newsDtoList = new ArrayList<>();

        newsList.forEach(news -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setAuthorDto(authorMapper.convertToDto(news.getAuthor()));
            newsDto.setTitle(news.getTitle());
            newsDto.setDescription(news.getDescription());
            newsDto.setCreateTime(news.getCreateTime());
            newsDto.setUpdateTime(news.getUpdateTime());
            newsDtoList.add(newsDto);

        });

        return newsDtoList;
    }

}
