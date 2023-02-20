package com.joaoptgaino.bookstore.fixtures.address;

import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.entities.AddressEntity;

public class AddressFixture {

    public static AddressEntity getAddressEntity(String street, String streetNumber, String neighborhood, String zipCode) {
        return AddressEntity.builder()
                .street(street)
                .streetNumber(streetNumber)
                .neighborhood(neighborhood)
                .zipCode(zipCode)
                .build();
    }

    public static AddressDTO getAddressDTO(String street, String streetNumber, String neighborhood, String zipCode) {
        return AddressDTO.builder()
                .street(street)
                .streetNumber(streetNumber)
                .neighborhood(neighborhood)
                .zipCode(zipCode)
                .build();
    }
}
