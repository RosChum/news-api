package com.example.newsapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "createTime")
    private Instant createTime;


    @OneToMany(mappedBy = "author")
    private List<News> news;

}
