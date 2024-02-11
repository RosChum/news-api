package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.News;
import com.example.newsapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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
        news.setCreateTime(ZonedDateTime.now());
        news.setUpdateTime(ZonedDateTime.now());

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
}
