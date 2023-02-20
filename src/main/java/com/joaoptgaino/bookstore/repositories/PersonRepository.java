package com.joaoptgaino.bookstore.repositories;

import com.joaoptgaino.bookstore.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
