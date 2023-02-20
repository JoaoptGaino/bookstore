package com.joaoptgaino.bookstore.repositories.specifications;

import com.joaoptgaino.bookstore.dtos.book.BookParamsDTO;
import com.joaoptgaino.bookstore.entities.BookEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@UtilityClass
public class BookSpecification {
    public static Specification<BookEntity> create(BookParamsDTO bookParamsDTO) {
        return hasTitle(bookParamsDTO.getTitle())
                .and(hasSummary(bookParamsDTO.getSummary()));
    }

    private static Specification<BookEntity> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (isNotBlank(title)) {
                return criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + title.toUpperCase() + "%");
            }
            return null;
        };
    }

    private static Specification<BookEntity> hasSummary(String summary) {
        return (root, query, criteriaBuilder) -> {
            if (isNotBlank(summary)) {
                return criteriaBuilder.like(criteriaBuilder.upper(root.get("summary")), "%" + summary.toUpperCase() + "%");
            }
            return null;
        };
    }
}
