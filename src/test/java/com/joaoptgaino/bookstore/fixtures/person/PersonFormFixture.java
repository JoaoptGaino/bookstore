package com.joaoptgaino.bookstore.fixtures.person;

import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;

public class PersonFormFixture {
    public static PersonFormDTO getPersonForm(String name, String type) {
        return PersonFormDTO.builder()
                .name(name)
                .type(type)
                .build();
    }
}
