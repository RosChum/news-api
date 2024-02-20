package com.example.newsapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;

    @Valid
    private ShortAuthorDto shortAuthorDto;

    @NotNull(message = "id новости должно быть указано")
    @Positive(message = "id новости должно быть больше 0")
    private Long newsId;

    @NotBlank(message = "Текст комментария не должен быть пустым")
    private String text;

    private Instant timeCreated;
}
