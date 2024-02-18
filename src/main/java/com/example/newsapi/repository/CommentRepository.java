package com.example.newsapi.repository;

import com.example.newsapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long> {

    Integer countByNews_Id(Long newsId);


}
