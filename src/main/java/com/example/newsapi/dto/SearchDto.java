package com.example.newsapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    private List<String> newsCategory;


}
