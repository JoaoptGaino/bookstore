package com.joaoptgaino.bookstore.services.book;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {
    BookDTO create(BookFormDTO data);

    Page<BookDTO> findAll(Pageable pageable);

    BookDTO findOne(UUID id);

    BookDTO update(UUID id, BookFormDTO data);

    void delete(UUID id);
}
