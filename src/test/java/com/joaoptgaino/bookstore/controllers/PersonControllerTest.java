package com.joaoptgaino.bookstore.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonDTO;
import com.joaoptgaino.bookstore.dtos.person.PersonFormDTO;
import com.joaoptgaino.bookstore.entities.PersonEntity;
import com.joaoptgaino.bookstore.services.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorDTO;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFormDTOFixture.getAuthorFormDTO;
import static com.joaoptgaino.bookstore.fixtures.person.PersonFixture.*;
import static com.joaoptgaino.bookstore.fixtures.person.PersonFormFixture.getPersonForm;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class PersonControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder()
            .create();

    @Mock
    private PersonService personService;

    private static final String ENDPOINT_PERSON = "/persons";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void savePersonShouldReturnSuccessful() throws Exception {
        PersonDTO person = getPersonDTO("Person", "Customer");
        PersonFormDTO formDTO = getPersonForm("Person", "Customer");
        String personRequest = gson.toJson(person);

        when(personService.create(formDTO)).thenReturn(person);

        RequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_PERSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(personRequest);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(person.getName()));
    }

    @Test
    public void findAllPersonsShouldReturnSuccessful() throws Exception {
        PersonDTO person = getPersonDTO("Person", "Customer");
        Page<PersonDTO> personResponse = new PageImpl<>(List.of(person));
        Pageable pageable = PageRequest.of(0, 10);

        when(personService.findAll(pageable)).thenReturn(personResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_PERSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Person"));
    }

    @Test
    public void findOnePersonShouldReturnSuccessful() throws Exception {
        PersonDTO person = getPersonDTO("Person", "Customer");
        when(personService.findOne(DEFAULT_PERSON_ID)).thenReturn(person);

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("%s/%s", ENDPOINT_PERSON, DEFAULT_PERSON_ID))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(person.getName()));
    }

    @Test
    public void updateShouldReturnSuccessful() throws Exception {
        PersonDTO personUpdated = getPersonDTO("Person Updated", "Customer");
        PersonFormDTO formDTO = getPersonForm("Person Updated", "Customer");

        String personRequest = gson.toJson(personUpdated);

        when(personService.update(DEFAULT_PERSON_ID, formDTO)).thenReturn(personUpdated);

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", ENDPOINT_PERSON, DEFAULT_PERSON_ID))
                .contentType(MediaType.APPLICATION_JSON).content(personRequest);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Person Updated"));
    }

    @Test
    public void deleteShouldSuccessful() throws Exception {
        doNothing().when(personService).delete(DEFAULT_PERSON_ID);

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", ENDPOINT_PERSON, DEFAULT_PERSON_ID));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(personService, times(1)).delete(DEFAULT_PERSON_ID);
    }
}
