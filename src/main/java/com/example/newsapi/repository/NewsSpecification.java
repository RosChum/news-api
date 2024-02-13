package com.example.newsapi.repository;

import com.example.newsapi.model.Author;
import com.example.newsapi.model.News;
import com.example.newsapi.model.News_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class NewsSpecification {


    public static <T> Specification<T> getSpecificationLike(SingularAttribute<T, String> field, String value) {
        if (checkValueNull(value)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), "%" + value + "%");

    }

    public static Specification<News> getSpecificationBetween(SingularAttribute<News, ZonedDateTime> field
            , ZonedDateTime timeFrom, ZonedDateTime timeTo) {

        if (checkValueNull(timeFrom) && checkValueNull(timeTo)) {
            return null;
        }

        if (timeFrom == null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(field), timeTo);
        }
        if (timeTo == null) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), timeFrom);
        }
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.between(root.get(field), timeFrom, timeTo);

    }


    public static Specification<News> joinSpecificationAuthors(String name, String value) {
        if (checkValueNull(value)) return null;
        return (root, query, criteriaBuilder) -> {
            Join<News, Author> joinTable = root.join(News_.AUTHOR);

            return criteriaBuilder.like(joinTable.get(name), "%" + value + "%");
        };
    }


    public static boolean checkValueNull(Object o) {
        return o == null;
    }


}
