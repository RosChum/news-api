package com.example.newsapi.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;

    private Long authorId;

    private String text;

    private Instant timeCreated;
}
