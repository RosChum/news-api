package com.example.newsapi.repository;

import com.example.newsapi.model.NewsСategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsСategory,Long> {
}
