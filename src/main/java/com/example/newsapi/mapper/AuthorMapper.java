package com.example.newsapi.mapper;

import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.dto.ShortAuthorDto;
import com.example.newsapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface AuthorMapper {


    @Mapping(target = "createTime", expression = "java(Instant.now())")
    @Mapping(target = "news", expression = "java(author.getNews() == null ? new ArrayList<>():author.getNews())")
    Author convertToEntity(AuthorDto authorDto);

    AuthorDto convertToDto(Author author);

    ShortAuthorDto convertToShortDto(Author author);

    List<AuthorDto> convertToListDto(List<Author> authorList);
}
