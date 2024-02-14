package com.example.newsapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class NewsDto {

    private String title;

    private String description;

    private ShortAuthorDto shortAuthorDto;

    private Instant createTime;

    private Instant updateTime;

}
