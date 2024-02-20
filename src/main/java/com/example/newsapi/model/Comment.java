package com.example.newsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "text")
    private String text;

    @Column(name = "time_created")
    private Instant timeCreated;

    @ManyToOne
    @JoinColumn(columnDefinition = "news_id", referencedColumnName = "id")
    private News news;
}
