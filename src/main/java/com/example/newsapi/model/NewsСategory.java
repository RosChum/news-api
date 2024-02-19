package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @ManyToMany(mappedBy = "newsСategoryList")
    private List<News> news;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsСategory that = (NewsСategory) o;
        return newsCategory.equalsIgnoreCase(that.newsCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newsCategory, news);
    }
}
