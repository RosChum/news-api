package com.example.newsapi.specifacation;

import com.example.newsapi.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import javax.xml.catalog.Catalog;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class BaseSpecification {


    public static <T> Specification<T> getEqual(SingularAttribute<T, String> field, String value) {
        return checkValueNull(value, () -> (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get(field)), value.toLowerCase().trim());
        });
    }

    public static <T> Specification<T> getLike(SingularAttribute<T, String> field, String value) {
        return checkValueNull(value, () -> {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.like(root.get(field), value);
            };
        });
    }

    public static <T> Specification<T> getBetween(SingularAttribute<T, Instant> field
            , Instant timeFrom, Instant timeTo) {

        return checkValueNull(timeFrom, timeTo, () -> {

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
        });
    }


    public static <T, V> Specification<T> getInCategoryNews( Collection<V> value){
     return checkValueNull(value, ()->{
            return (root, query, criteriaBuilder) -> {
                query.distinct(true);
                Join<News, NewsСategory> newsСategoryJoin = root.join(News_.NEWSСATEGORY_LIST);
              return newsСategoryJoin.get(NewsСategory_.NEWS_CATEGORY).in(value);
            };
        });
    }


    public static Specification<News> joinAuthors(String name, String value) {
        return checkValueNull(value, () -> {
            return (root, query, criteriaBuilder) -> {
                Join<News, Author> joinTable = root.join(News_.AUTHOR);
                return criteriaBuilder.like(joinTable.get(name), "%" + value + "%");
            };
        });
    }

    // TODO здесь не правильно

    public static Specification<Author> joinNews(Long authorId) {
        return checkValueNull(authorId, () -> {
            return (root, query, criteriaBuilder) -> {
                Join<Author, News> newsJoin = root.join(Author_.news);
                return criteriaBuilder.equal(newsJoin.get(News_.author), authorId);
            };
        });
    }


    private static <T, V> Specification<T> checkValueNull(V value, Supplier<Specification<T>> supplier) {
        return value == null ? ((root, query, builder) -> null) : supplier.get();
    }

    private static <T, V> Specification<T> checkValueNull(V value1, V value2, Supplier<Specification<T>> supplier) {
        return value1 == null && value2 == null ? ((root, query, builder) -> null) : supplier.get();
    }

}
