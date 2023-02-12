package com.joaoptgaino.bookstore.repositories;

import com.joaoptgaino.bookstore.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
}
