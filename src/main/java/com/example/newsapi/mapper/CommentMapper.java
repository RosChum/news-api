package com.example.newsapi.mapper;

import com.example.newsapi.dto.CommentDto;
import com.example.newsapi.model.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto convertToDto(Comment comment);


    List<CommentDto> convertToListDto(List<Comment> comments);
}
