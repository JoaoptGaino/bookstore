package com.joaoptgaino.bookstore.fixtures.person;

import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.entities.AddressEntity;
import com.joaoptgaino.bookstore.entities.PersonEntity;

import java.util.List;
import java.util.UUID;

public class PersonFixture {
    public static UUID DEFAULT_PERSON_ID = UUID.fromString("4da12404-d173-48ce-9e87-5497eba3eb4e");

    public static PersonEntity getPersonEntity(String name, String type) {
        return PersonEntity.builder()
                .id(DEFAULT_PERSON_ID)
                .name(name)
                .type(type)
                .build();
    }

    public static PersonEntity getPersonEntityWithAddress(String name, String type, AddressEntity address) {
        return PersonEntity.builder()
                .id(DEFAULT_PERSON_ID)
                .name(name)
                .type(type)
                .address(List.of(address))
                .build();
    }

    public static PersonDTO getPersonDTO(String name, String type) {
        return PersonDTO.builder()
                .id(DEFAULT_PERSON_ID)
                .name(name)
                .type(type)
                .build();
    }

    public static PersonDTO getPersonDTOWithAddress(String name, String type, AddressDTO addressDTO) {
        return PersonDTO.builder()
                .id(DEFAULT_PERSON_ID)
                .name(name)
                .type(type)
                .address(List.of(addressDTO))
                .build();
    }
}
