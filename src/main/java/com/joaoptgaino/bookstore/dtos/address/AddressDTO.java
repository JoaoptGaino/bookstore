package com.joaoptgaino.bookstore.dtos.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private UUID id;

    private String street;

    private String streetNumber;

    private String neighborhood;

    private String zipCode;

    private Date createdAt;

    private Date updatedAt;
}
