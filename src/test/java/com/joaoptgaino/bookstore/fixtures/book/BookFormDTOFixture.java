package com.joaoptgaino.bookstore.fixtures.book;

import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;

public class BookFormDTOFixture {
    public static BookFormDTO getBookFormDTO(String title, String summary) {
        return BookFormDTO.builder()
                .title(title)
                .summary(summary)
                .build();
    }

    public static BookFormDTO getBookFormDTOWithAuthorId(String title, String summary) {
        return BookFormDTO.builder()
                .title(title)
                .summary(summary)
                .authorId(DEFAULT_AUTHOR_ID)
                .build();
    }
}
