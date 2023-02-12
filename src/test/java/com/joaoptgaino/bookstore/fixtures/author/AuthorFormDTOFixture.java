package com.joaoptgaino.bookstore.fixtures.author;

import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;

public class AuthorFormDTOFixture {
    public static AuthorFormDTO getAuthorFormDTO(String name) {
        AuthorFormDTO authorFormDTO = new AuthorFormDTO();
        authorFormDTO.setName(name);
        return authorFormDTO;
    }
}
