package com.example.newsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(columnDefinition = "author_id", referencedColumnName = "id")
    private Author author;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @ManyToMany
    @JoinTable(name = "category_news", joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<NewsСategory> newsСategoryList;


}
