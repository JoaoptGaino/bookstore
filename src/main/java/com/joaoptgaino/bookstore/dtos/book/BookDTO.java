package com.joaoptgaino.bookstore.dtos.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private UUID id;
    private String title;
    private String summary;
    private String authorName;
    private Date createdAt;
    private Date updatedAt;
}
