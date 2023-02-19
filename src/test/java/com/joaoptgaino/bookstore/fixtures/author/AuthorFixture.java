package com.joaoptgaino.bookstore.fixtures.author;

import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;

import java.util.UUID;

public class AuthorFixture {
    public static UUID DEFAULT_AUTHOR_ID = UUID.fromString("d4a46834-98a0-4f9a-bbf8-2dc3bc4ad7b8");

    public static AuthorEntity getAuthorEntity(String name) {
        return AuthorEntity.builder()
                .id(DEFAULT_AUTHOR_ID)
                .name(name)
                .build();
    }

    public static AuthorDTO getAuthorDTO(String name) {
        return AuthorDTO.builder().id(DEFAULT_AUTHOR_ID).name(name).build();
    }
}
