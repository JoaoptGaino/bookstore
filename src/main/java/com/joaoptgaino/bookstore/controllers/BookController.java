package com.joaoptgaino.bookstore.controllers;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;
import com.joaoptgaino.bookstore.dtos.book.BookParamsDTO;
import com.joaoptgaino.bookstore.services.book.BookService;
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
@RequestMapping("/books")
@AllArgsConstructor
@NoArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    ResponseEntity<BookDTO> create(@RequestBody @Valid BookFormDTO data) {
        BookDTO book = bookService.create(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    ResponseEntity<Page<BookDTO>> findAll(@Valid BookParamsDTO paramsDTO, @PageableDefault() Pageable pageable) {
        Page<BookDTO> books = bookService.findAll(paramsDTO, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookDTO> findOne(@PathVariable(value = "id") UUID id) {
        BookDTO book = bookService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookDTO> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookFormDTO data) {
        BookDTO book = bookService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<BookDTO> delete(@PathVariable(value = "id") UUID id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
