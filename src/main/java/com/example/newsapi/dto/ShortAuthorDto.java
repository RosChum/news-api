package com.example.newsapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShortAuthorDto {

    @NotNull(message = "Id автора не должно быть пустым")
    @Positive(message = "Id автора должно быт больше 0")
    private Long id;

    private String firstName;

    private String lastName;
}
