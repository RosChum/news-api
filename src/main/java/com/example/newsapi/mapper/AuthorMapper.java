package com.example.newsapi.mapper;

import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.dto.ShortAuthorDto;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface AuthorMapper {


    @Mapping(target = "createTime", expression = "java(Instant.now())")
    @Mapping(target = "news", expression = "java(author.getNews() == null ? new ArrayList<>():author.getNews())")
    @Mapping(target = "id", ignore = true)
    Author createEntity(AuthorDto authorDto);

    Author convertToEntity(AuthorDto authorDto);
    Author convertFromUserToEntity(User user);
    AuthorDto convertToDto(Author author);

    ShortAuthorDto convertToShortDto(Author author);

    AuthorDto shortAuthorDtoConvertToDto(ShortAuthorDto shortAuthorDto);

    List<AuthorDto> convertToListDto(List<Author> authorList);
}
