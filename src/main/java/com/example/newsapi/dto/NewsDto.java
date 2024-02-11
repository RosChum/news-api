package com.example.newsapi.dto;

import com.example.newsapi.model.Author;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class NewsDto {

    private String title;

    private String description;

    private AuthorDto authorDto;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

}
