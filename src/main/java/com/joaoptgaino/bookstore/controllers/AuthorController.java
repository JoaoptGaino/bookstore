package com.joaoptgaino.bookstore.controllers;

import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.services.author.AuthorService;
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

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
@NoArgsConstructor
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    ResponseEntity<AuthorDTO> create(@RequestBody @Valid AuthorFormDTO data) {
        AuthorDTO author = authorService.create(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @GetMapping
    ResponseEntity<Page<AuthorDTO>> findAll(@PageableDefault() Pageable pageable) {
        Page<AuthorDTO> authors = authorService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/{id}")
    ResponseEntity<AuthorDTO> findOne(@PathVariable(value = "id") UUID id) {
        AuthorDTO author = authorService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PutMapping("/{id}")
    ResponseEntity<AuthorDTO> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid AuthorFormDTO data) {
        AuthorDTO author = authorService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<AuthorDTO> delete(@PathVariable(value = "id") UUID id) {
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
