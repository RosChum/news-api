package com.example.newsapi.dto;

import com.example.newsapi.model.TypeNewsCategory;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class SearchDto {

    private Long authorId;

    private Long itemId;

    private String titleNews;

    private String descriptionNews;

    private String firstNameAuthor;

    private String lastNameAuthor;

    private String timeFrom;

    private String timeTo;

    private String newsCategory;


}
