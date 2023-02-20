package com.joaoptgaino.bookstore.services.person;

import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.dtos.address.AddressFormDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;
import com.joaoptgaino.bookstore.entities.AddressEntity;
import com.joaoptgaino.bookstore.entities.PersonEntity;
import com.joaoptgaino.bookstore.repositories.AddressRepository;
import com.joaoptgaino.bookstore.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public PersonDTO create(PersonFormDTO data) {
        PersonEntity person = modelMapper.map(data, PersonEntity.class);
        personRepository.save(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public Page<PersonDTO> findAll(Pageable pageable) {
        Page<PersonEntity> persons = personRepository.findAll(pageable);
        return new PageImpl<>(persons.getContent().stream().map(person -> modelMapper.map(person, PersonDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public PersonDTO findOne(UUID id) {
        PersonEntity person = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person not found"));

        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public PersonDTO update(UUID id, PersonFormDTO data) {
        PersonEntity person = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person not found"));
        Date date = new Date();
        person.setName(data.getName());
        person.setType(data.getType());
        person.setUpdatedAt(date);

        personRepository.save(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public void delete(UUID id) {
        personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person not found"));
        personRepository.deleteById(id);
    }

    @Override
    public AddressDTO createAddresses(UUID id, AddressFormDTO data) {
        PersonEntity person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person not found"));
        AddressEntity address = modelMapper.map(data, AddressEntity.class);
        address.setPerson(person);

        addressRepository.save(address);
        return modelMapper.map(address, AddressDTO.class);
    }
}
