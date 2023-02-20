package com.joaoptgaino.bookstore.dtos.person;

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
public class PersonFormDTO {
    @Size(min = 2,max = 255,message = "name size must be between 2 and 255")
    @NotBlank(message = "name is mandatory")
    private String name;

    @Size(min = 2,max = 255,message = "type size must be between 2 and 255")
    @NotBlank(message = "type is mandatory")
    private String type;
}
