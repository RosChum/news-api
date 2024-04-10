package com.example.newsapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class NewsDto {

    private Long id;

    @NotBlank(message = "Заголовок новости не должен быть пустой")
    private String title;

    @NotBlank(message = "Описание новости не должно быть пустым")
    private String description;

    @Valid
    private ShortAccountDto shortAccountDto;

    private Instant createTime;

    private Instant updateTime;

    private List<NewsCategoryDto> newsCategory;

    private Integer countComment;

    private List<CommentDto> commentDtos;

}
