package com.joaoptgaino.bookstore.dtos.author;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {
    private UUID id;
    private String name;
    private List<BookDTO> books;
    private Date createdAt;
    private Date updatedAt;
}
