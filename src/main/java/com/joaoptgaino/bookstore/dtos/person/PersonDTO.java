package com.joaoptgaino.bookstore.dtos.person;

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
public class PersonDTO {
    private UUID id;
    private String name;
    private String type;
    //private List<AddressDTO> addresses;
    private Date createdAt;
    private Date updatedAt;
}
