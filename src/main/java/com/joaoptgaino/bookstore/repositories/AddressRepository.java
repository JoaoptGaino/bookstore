package com.joaoptgaino.bookstore.repositories;

import com.joaoptgaino.bookstore.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
