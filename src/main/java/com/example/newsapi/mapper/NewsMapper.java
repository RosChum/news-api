package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.ShortNewsDto;
import com.example.newsapi.model.News;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", uses = AccountMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    NewsDto convertToDto(News news);

    News convertToEntity(NewsDto dto);

    List<NewsDto> convertToListDto(List<News> newsList);

    List<ShortNewsDto> convertToShortDto(List<News> newsList);


}
