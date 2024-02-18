package com.example.newsapi.mapper;

import com.example.newsapi.dto.NewsCategoryDto;
import com.example.newsapi.model.NewsСategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsCategoryMapper {

    NewsСategory convertToEntity(NewsCategoryDto category);

    List<NewsСategory> convertToListEntity(List<NewsCategoryDto> categoryList);

    NewsCategoryDto convertToDto(NewsСategory newsСategory);

    List<NewsCategoryDto> convertToListDto(List<NewsСategory> newsСategories);
}
