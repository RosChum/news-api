package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.News;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.HashMap;
import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", uses = AuthorMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    NewsDto convertToDto(News news);

    HashMap<Author, News> convertToEntity(NewsDto dto);

    List<NewsDto> convertToListDto(List<News> newsList);




}
