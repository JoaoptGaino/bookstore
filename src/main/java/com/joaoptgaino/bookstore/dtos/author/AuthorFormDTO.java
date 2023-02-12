package com.joaoptgaino.bookstore.dtos.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorFormDTO {

    @Size(min = 2,max = 255,message = "name size must be between 2 and 255")
    @NotBlank(message = "name is mandatory")
    private String name;
}
