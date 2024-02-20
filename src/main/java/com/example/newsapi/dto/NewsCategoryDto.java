package com.example.newsapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsCategoryDto {

    private Long id;

    @NotBlank(message = "Категория новости не должна быть пустой")
    private String newsCategory;

}
