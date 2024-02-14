package com.example.newsapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShortAuthorDto {
    private Long id;

    private String firstName;

    private String lastName;
}
