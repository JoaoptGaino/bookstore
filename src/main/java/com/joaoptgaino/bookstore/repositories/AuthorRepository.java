package com.joaoptgaino.bookstore.repositories;

import com.joaoptgaino.bookstore.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {
}
