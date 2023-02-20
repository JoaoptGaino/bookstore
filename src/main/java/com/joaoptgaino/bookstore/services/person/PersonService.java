package com.joaoptgaino.bookstore.services.person;

import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.dtos.address.AddressFormDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PersonService {
    PersonDTO create(PersonFormDTO data);

    Page<PersonDTO> findAll(Pageable pageable);

    PersonDTO findOne(UUID id);

    PersonDTO update(UUID id, PersonFormDTO data);

    void delete(UUID id);

    AddressDTO createAddresses(UUID id, AddressFormDTO data);
}
