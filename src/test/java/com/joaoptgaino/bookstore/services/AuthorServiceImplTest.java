package com.joaoptgaino.bookstore.services;

import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.repositories.AuthorRepository;
import com.joaoptgaino.bookstore.services.author.AuthorService;
import com.joaoptgaino.bookstore.services.author.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorEntity;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFormDTOFixture.getAuthorFormDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthorServiceImplTest {
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        authorService = new AuthorServiceImpl(authorRepository, modelMapper);
    }

    @Test
    public void createAuthorShouldReturnSuccessful() {
        AuthorEntity author = getAuthorEntity("Chuck Palahniuk");
        AuthorFormDTO form = getAuthorFormDTO("Chuck Palahniuk");
        when(authorRepository.save(author)).thenReturn(author);

        AuthorDTO response = authorService.create(form);

        assertThat(response.getName()).isEqualTo("Chuck Palahniuk");
    }

    @Test
    public void findAllAuthorsShouldReturnSuccessful() {
        AuthorEntity author = getAuthorEntity("Chuck Palahniuk");
        Pageable pageable = PageRequest.of(0, 10);
        Page<AuthorEntity> page = new PageImpl<>(List.of(author));

        when(authorRepository.findAll(pageable)).thenReturn(page);

        Page<AuthorDTO> response = authorService.findAll(pageable);

        assertThat(response.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findOneShouldReturnSuccessful() {
        AuthorEntity author = getAuthorEntity("Chuck Palahniuk");

        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.of(author));

        AuthorDTO response = authorService.findOne(DEFAULT_AUTHOR_ID);

        assertThat(response.getName()).isEqualTo("Chuck Palahniuk");
    }

    @Test
    public void findOneShouldReturnNotFound() {
        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findOne(DEFAULT_AUTHOR_ID))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void updateShouldReturnSuccessful() {
        AuthorEntity author = getAuthorEntity("Chuck Palah");
        AuthorEntity authorUpdated = getAuthorEntity("Chuck Palahniuk");
        AuthorFormDTO formDTO = getAuthorFormDTO("Chuck Palahniuk");

        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(authorRepository.save(authorUpdated)).thenReturn(authorUpdated);

        AuthorDTO response = authorService.update(DEFAULT_AUTHOR_ID, formDTO);
        assertThat(response.getName()).isEqualTo("Chuck Palahniuk");
    }

    @Test
    public void updateShouldReturnNotFound() {
        AuthorFormDTO formDTO = getAuthorFormDTO("Chuck Palahniuk");

        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.update(DEFAULT_AUTHOR_ID, formDTO))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void deleteByIdShouldBeCalled() {
        AuthorEntity author = getAuthorEntity("Chuck Palahniuk");

        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).deleteById(DEFAULT_AUTHOR_ID);

        authorService.delete(DEFAULT_AUTHOR_ID);
        verify(authorRepository, times(1)).deleteById(DEFAULT_AUTHOR_ID);
    }

    @Test
    public void deleteByIdShouldReturnNotFound() {
        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.empty());
        doNothing().when(authorRepository).deleteById(DEFAULT_AUTHOR_ID);

        assertThatThrownBy(() -> authorService.delete(DEFAULT_AUTHOR_ID))
                .isInstanceOf(ResponseStatusException.class);
    }
}
