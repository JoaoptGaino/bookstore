package com.joaoptgaino.bookstore.dtos.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressFormDTO {

    @Size(min = 2, max = 255, message = "street size must be between 2 and 255")
    @NotBlank(message = "street is mandatory")
    private String street;

    @Size(min = 2, max = 255, message = "streetNumber size must be between 2 and 255")
    @NotBlank(message = "streetNumber is mandatory")
    private String streetNumber;

    @Size(min = 2, max = 255, message = "neighborhood size must be between 2 and 255")
    @NotBlank(message = "neighborhood is mandatory")
    private String neighborhood;

    @Size(min = 2, max = 255, message = "zipCode size must be between 2 and 255")
    @NotBlank(message = "zipCode is mandatory")
    private String zipCode;
}
