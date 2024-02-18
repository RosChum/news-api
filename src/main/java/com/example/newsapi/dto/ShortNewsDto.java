package com.example.newsapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
@Getter
@Setter
@ToString
public class ShortNewsDto {

    private Long id;

    private String title;

    private String description;

    private Instant createTime;

    private Instant updateTime;


}
