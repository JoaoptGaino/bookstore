package com.joaoptgaino.bookstore.services;

import com.joaoptgaino.bookstore.dtos.address.AddressDTO;
import com.joaoptgaino.bookstore.dtos.address.AddressFormDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;
import com.joaoptgaino.bookstore.entities.AddressEntity;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.entities.PersonEntity;
import com.joaoptgaino.bookstore.fixtures.person.PersonFormFixture;
import com.joaoptgaino.bookstore.repositories.AddressRepository;
import com.joaoptgaino.bookstore.repositories.PersonRepository;
import com.joaoptgaino.bookstore.services.person.PersonService;
import com.joaoptgaino.bookstore.services.person.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.joaoptgaino.bookstore.fixtures.address.AddressFixture.getAddressEntity;
import static com.joaoptgaino.bookstore.fixtures.address.AddressFormFixtureDTO.getAddressForm;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorEntity;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFormDTOFixture.getAuthorFormDTO;
import static com.joaoptgaino.bookstore.fixtures.person.PersonFixture.DEFAULT_PERSON_ID;
import static com.joaoptgaino.bookstore.fixtures.person.PersonFixture.getPersonEntity;
import static com.joaoptgaino.bookstore.fixtures.person.PersonFormFixture.getPersonForm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceImplTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        personService = new PersonServiceImpl(personRepository, addressRepository, modelMapper);
    }

    @Test
    public void createPersonShouldReturnSuccessful() {
        PersonEntity person = getPersonEntity("Person", "Customer");
        PersonFormDTO formDTO = getPersonForm("Person", "Customer");

        when(personRepository.save(person)).thenReturn(person);

        PersonDTO response = personService.create(formDTO);

        assertThat(response.getName()).isEqualTo("Person");
    }

    @Test
    public void findAllPersonsShouldReturnSuccessful() {
        PersonEntity person = getPersonEntity("Person", "Customer");
        Pageable pageable = PageRequest.of(0, 10);
        Page<PersonEntity> page = new PageImpl<>(List.of(person));

        when(personRepository.findAll(pageable)).thenReturn(page);

        Page<PersonDTO> response = personService.findAll(pageable);

        assertThat(response.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findOneShouldReturnSuccessful() {
        PersonEntity person = getPersonEntity("Person", "Customer");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.of(person));

        PersonDTO response = personService.findOne(DEFAULT_PERSON_ID);

        assertThat(response.getName()).isEqualTo("Person");
    }

    @Test
    public void findOneShouldReturnNotFound() {
        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.findOne(DEFAULT_PERSON_ID))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void updateShouldReturnSuccessful() {
        PersonEntity person = getPersonEntity("Person", "Customer");
        PersonEntity updatedEntity = getPersonEntity("Person updated", "Customer");
        PersonFormDTO formDTO = getPersonForm("Person updated", "Customer");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.of(person));
        when(personRepository.save(updatedEntity)).thenReturn(updatedEntity);

        PersonDTO response = personService.update(DEFAULT_PERSON_ID, formDTO);

        assertThat(response.getName()).isEqualTo("Person updated");
    }

    @Test
    public void updateShouldReturnNotFound() {
        PersonFormDTO formDTO = getPersonForm("Person updated", "Customer");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> personService.update(DEFAULT_PERSON_ID, formDTO))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void deleteByIdShouldBeCalled() {
        PersonEntity person = getPersonEntity("Person", "Customer");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).deleteById(DEFAULT_PERSON_ID);

        personService.delete(DEFAULT_PERSON_ID);
        verify(personRepository, times(1)).deleteById(DEFAULT_PERSON_ID);
    }

    @Test
    public void deleteByIdShouldReturnNotFound() {
        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.empty());
        doNothing().when(personRepository).deleteById(DEFAULT_PERSON_ID);

        assertThatThrownBy(() -> personService.delete(DEFAULT_PERSON_ID))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void createAddressesShouldReturnSuccessful() {
        PersonEntity person = getPersonEntity("Person", "Customer");
        AddressEntity address = getAddressEntity("Street", "01", "Neighborhood", "10000");
        AddressFormDTO formDTO = getAddressForm("Street", "01", "Neighborhood", "10000");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.of(person));
        when(addressRepository.save(address)).thenReturn(address);

        AddressDTO response = personService.createAddresses(DEFAULT_PERSON_ID, formDTO);

        assertThat(response.getStreet()).isEqualTo("Street");
    }

    @Test
    public void createAddressShouldReturnNotFound() {
        AddressEntity address = getAddressEntity("Street", "01", "Neighborhood", "10000");
        AddressFormDTO formDTO = getAddressForm("Street", "01", "Neighborhood", "10000");

        when(personRepository.findById(DEFAULT_PERSON_ID)).thenReturn(Optional.empty());
        when(addressRepository.save(address)).thenReturn(address);

        assertThatThrownBy(() -> personService.createAddresses(DEFAULT_PERSON_ID, formDTO))
                .isInstanceOf(ResponseStatusException.class);
    }
}
