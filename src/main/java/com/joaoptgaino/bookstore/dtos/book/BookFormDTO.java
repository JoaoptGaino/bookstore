package com.joaoptgaino.bookstore.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFormDTO {
    @Size(min = 2, max = 255, message = "title size must be between 2 and 255")
    @NotBlank(message = "title is mandatory")
    private String title;

    @Size(min = 2, max = 255, message = "summary size must be between 2 and 255")
    @NotBlank(message = "summary is mandatory")
    private String summary;

    @Size(min = 15, max = 15, message = "authorId size must be between 2 and 15")
    private UUID authorId;
}
