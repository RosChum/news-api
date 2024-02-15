package com.example.newsapi.dto;

import com.example.newsapi.model.News;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class AuthorDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String createTime;

    private List<ShortNewsDto> news;

}
