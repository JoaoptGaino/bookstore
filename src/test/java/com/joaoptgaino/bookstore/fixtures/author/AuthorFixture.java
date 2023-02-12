package com.joaoptgaino.bookstore.fixtures.author;

import com.joaoptgaino.bookstore.entities.AuthorEntity;

import java.util.UUID;

public class AuthorFixture {
    public static UUID DEFAULT_AUTHOR_ID = UUID.fromString("d4a46834-98a0-4f9a-bbf8-2dc3bc4ad7b8");

    public static AuthorEntity getAuthorEntity(String name) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(DEFAULT_AUTHOR_ID);
        entity.setName(name);
        return entity;
    }
}
