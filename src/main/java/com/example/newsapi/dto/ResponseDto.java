package com.example.newsapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDto {

    private List<NewsDto> newsDto;

    private AuthorDto authorDto;


}
