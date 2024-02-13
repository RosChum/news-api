package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;
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

    @Column(name = "createTime")
    private Instant createTime;

    @Column(name = "updateTime")
    private Instant updateTime;

    @ManyToOne
    @JoinColumn(columnDefinition = "author_id", referencedColumnName = "id")
    private Author author;



}
