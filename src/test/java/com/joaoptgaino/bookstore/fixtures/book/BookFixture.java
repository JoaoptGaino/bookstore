package com.joaoptgaino.bookstore.fixtures.book;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.entities.BookEntity;

import java.util.UUID;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorEntity;

public class BookFixture {
    public static UUID DEFAULT_BOOK_ID = UUID.fromString("01a73479-1bd3-45ae-a035-db5f536f1888");

    public static BookEntity getBookEntity(String title, String summary) {
        return BookEntity.builder()
                .id(DEFAULT_BOOK_ID)
                .title(title)
                .summary(summary)
                .build();
    }

    public static BookEntity getBookEntityWithAuthor(String title, String summary, String authorName) {
        return BookEntity.builder()
                .id(DEFAULT_BOOK_ID)
                .title(title)
                .summary(summary)
                .author(getAuthorEntity(authorName))
                .build();
    }

    public static BookDTO getBookDTO(String title, String summary, String authorName) {
        return BookDTO.builder()
                .id(DEFAULT_BOOK_ID)
                .title(title)
                .summary(summary)
                .authorName(authorName)
                .build();
    }
}
