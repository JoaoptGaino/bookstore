package com.joaoptgaino.bookstore.fixtures.address;

import com.joaoptgaino.bookstore.dtos.address.AddressFormDTO;

public class AddressFormFixtureDTO {

    public static AddressFormDTO getAddressForm(String street, String streetNumber, String neighborhood, String zipCode){
        return AddressFormDTO.builder()
                .street(street)
                .streetNumber(streetNumber)
                .neighborhood(neighborhood)
                .zipCode(zipCode)
                .build();
    }
}
