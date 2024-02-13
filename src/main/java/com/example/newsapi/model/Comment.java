package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "timeCreated")
    private Instant timeCreated;

    @ManyToOne
    @JoinColumn(columnDefinition = "news_id", referencedColumnName = "id")
    private News news;
}
