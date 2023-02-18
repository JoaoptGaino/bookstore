package com.joaoptgaino.bookstore.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.services.author.AuthorService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.Request;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
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

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.*;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFormDTOFixture.getAuthorFormDTO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder()
            .create();

    @Mock
    private AuthorService authorService;

    private static final String ENDPOINT_AUTHOR = "/authors";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(authorService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void saveAuthorShouldReturnSuccessful() throws Exception {
        AuthorDTO author = getAuthorDTO("Chuck Palahniuk");
        AuthorFormDTO formDTO = getAuthorFormDTO("Chuck Palahniuk");
        String authorRequest = gson.toJson(author);
        when(authorService.create(formDTO)).thenReturn(author);

        RequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_AUTHOR).contentType(MediaType.APPLICATION_JSON).content(authorRequest);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(author.getName()));
    }

    @Test
    public void findAllAuthorsShouldReturnSuccessful() throws Exception {
        AuthorDTO author = getAuthorDTO("Chuck Palahniuk");
        Page<AuthorDTO> authorResponse = new PageImpl<>(List.of(author));
        Pageable pageable = PageRequest.of(0, 10);

        when(authorService.findAll(pageable)).thenReturn(authorResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_AUTHOR).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Chuck Palahniuk"));
    }

    @Test
    public void findOneShouldReturnSuccessful() throws Exception {
        AuthorDTO author = getAuthorDTO("Chuck Palahniuk");

        when(authorService.findOne(DEFAULT_AUTHOR_ID)).thenReturn(author);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", ENDPOINT_AUTHOR, DEFAULT_AUTHOR_ID))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(author.getName()));
    }

    @Test
    public void updateShouldReturnSuccessful() throws Exception {
        AuthorDTO authorUpdated = getAuthorDTO("Chuck Palahniuk");
        AuthorFormDTO formDTO = getAuthorFormDTO("Chuck Palahniuk");
        String authorRequest = gson.toJson(authorUpdated);
        when(authorService.update(DEFAULT_AUTHOR_ID, formDTO)).thenReturn(authorUpdated);

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", ENDPOINT_AUTHOR, DEFAULT_AUTHOR_ID))
                .contentType(MediaType.APPLICATION_JSON).content(authorRequest);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Chuck Palahniuk"));
    }

    @Test
    public void deleteShouldSuccessful() throws Exception {
        doNothing().when(authorService).delete(DEFAULT_AUTHOR_ID);

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", ENDPOINT_AUTHOR, DEFAULT_AUTHOR_ID));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).delete(DEFAULT_AUTHOR_ID);
    }
}
