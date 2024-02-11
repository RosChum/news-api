package com.example.newsapi.repository;

import com.example.newsapi.model.News;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecification {


    public static <T, V> Specification<V> getSpecificationLike(SingularAttribute<T, V> field, String value) {
        if (!checkValueNull(value)) return null;

            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.like(root.get(String.valueOf(field)), "%" + value + "%");
            };

    }


    public static boolean checkValueNull(Object o) {
        if (o == null) {
            return false;
        }
        return true;
    }
}
