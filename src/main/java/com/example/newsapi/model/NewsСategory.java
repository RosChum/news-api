package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "news_category")
public class NewsСategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_news_category")
    private String newsCategory;

    @OneToMany(mappedBy = "newsСategoryList")
    private List<News> news;
}
