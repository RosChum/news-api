package com.example.newsapi.mapper;

import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface AuthorMapper {

    @Mapping(target = "createTime", expression = "java(Instant.now())")
    Author convertToEntity(AuthorDto authorDto);

    AuthorDto convertToDto(Author author);
}
