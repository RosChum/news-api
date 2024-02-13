package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news_category")
public class NewsСategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_news_category")
    private TypeNewsCategory typeNewsCategory;

    @ManyToOne
    @JoinColumn(columnDefinition = "news_id", referencedColumnName = "id")
    private News news;
}
