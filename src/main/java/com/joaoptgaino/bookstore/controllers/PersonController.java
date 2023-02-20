package com.joaoptgaino.bookstore.controllers;


import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.dtos.address.AddressFormDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;
import com.joaoptgaino.bookstore.services.person.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
@NoArgsConstructor
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonFormDTO data) {
        PersonDTO person = personService.create(data);
        return ResponseEntity.status(CREATED).body(person);
    }

    @GetMapping
    ResponseEntity<Page<PersonDTO>> findAll(@PageableDefault() Pageable pageable) {
        Page<PersonDTO> persons = personService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @GetMapping("/{id}")
    ResponseEntity<PersonDTO> findOne(@PathVariable(value = "id") UUID id) {
        PersonDTO person = personService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonDTO> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid PersonFormDTO data) {
        PersonDTO person = personService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PersonDTO> delete(@PathVariable(value = "id") UUID id) {
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/addresses")
    ResponseEntity<AddressDTO> createAddresses(@PathVariable(value = "id") UUID id, @RequestBody @Valid AddressFormDTO data) {
        AddressDTO address = personService.createAddresses(id, data);
        return ResponseEntity.status(CREATED).body(address);
    }
}
