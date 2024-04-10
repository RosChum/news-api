package com.example.newsapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account extends User {


    @Column(name = "create_time")
    private Instant createTime;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<News> news;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
