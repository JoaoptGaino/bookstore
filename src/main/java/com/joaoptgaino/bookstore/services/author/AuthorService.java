package com.joaoptgaino.bookstore.services.author;

import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthorService {
    AuthorDTO create(AuthorFormDTO data);

    Page<AuthorDTO> findAll(Pageable pageable);

    AuthorDTO findOne(UUID id);

    AuthorDTO update(UUID id, AuthorFormDTO data);

    void delete(UUID id);
}
