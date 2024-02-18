package com.example.newsapi.dto;

import com.example.newsapi.model.NewsСategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class NewsDto {

    private Long id;

    private String title;

    private String description;

    private ShortAuthorDto shortAuthorDto;

    private Instant createTime;

    private Instant updateTime;

    private List<NewsCategoryDto> newsCategory;

    private Integer countComment;

    private List<CommentDto> commentDtos;

}
