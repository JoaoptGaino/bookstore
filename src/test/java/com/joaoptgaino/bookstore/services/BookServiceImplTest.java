package com.joaoptgaino.bookstore.services;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;
import com.joaoptgaino.bookstore.dtos.book.BookParamsDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.entities.BookEntity;
import com.joaoptgaino.bookstore.repositories.AuthorRepository;
import com.joaoptgaino.bookstore.repositories.BookRepository;
import com.joaoptgaino.bookstore.repositories.specifications.BookSpecification;
import com.joaoptgaino.bookstore.services.book.BookService;
import com.joaoptgaino.bookstore.services.book.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorEntity;
import static com.joaoptgaino.bookstore.fixtures.book.BookFixture.DEFAULT_BOOK_ID;
import static com.joaoptgaino.bookstore.fixtures.book.BookFixture.getBookEntityWithAuthor;
import static com.joaoptgaino.bookstore.fixtures.book.BookFormDTOFixture.getBookFormDTOWithAuthorId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        bookService = new BookServiceImpl(bookRepository, authorRepository, modelMapper);
    }

    @Test
    public void createBookShouldReturnSuccessful() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        BookFormDTO form = getBookFormDTOWithAuthorId("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality");

        when(bookRepository.save(book)).thenReturn(book);
        BookDTO response = bookService.create(form);

        assertThat(response.getTitle()).isEqualTo("Fight Club");
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    public void findAllBooksShouldReturnSuccessful() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        Pageable pageable = PageRequest.of(0, 10);
        BookParamsDTO paramsDTO = new BookParamsDTO();
        Page<BookEntity> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<BookDTO> response = bookService.findAll(paramsDTO, pageable);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).getAuthorName()).isEqualTo("Chuck Palahniuk");
    }

    @Test
    public void findOneShouldReturnSuccessful() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");

        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.of(book));

        BookDTO response = bookService.findOne(DEFAULT_BOOK_ID);

        assertThat(response.getTitle()).isEqualTo("Fight Club");
    }

    @Test
    public void findOneShouldReturnNotFound() {
        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findOne(DEFAULT_BOOK_ID))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void updateShouldReturnSuccessful() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        AuthorEntity author = getAuthorEntity("Chuck Palahniuk");
        BookEntity bookUpdated = getBookEntityWithAuthor("Fight Club", "DO NOT TALK ABOUT IT", "Chuck Palahniuk");
        BookFormDTO formDTO = getBookFormDTOWithAuthorId("Fight Club", "DO NOT TALK ABOUT IT");

        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.of(book));
        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(bookRepository.save(bookUpdated)).thenReturn(bookUpdated);

        BookDTO response = bookService.update(DEFAULT_BOOK_ID, formDTO);
        assertThat(response.getAuthorName()).isEqualTo("Chuck Palahniuk");
        assertThat(response.getTitle()).isEqualTo("Fight Club");
    }

    @Test
    public void updateShouldReturnAuthorNotFound() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        BookFormDTO formDTO = getBookFormDTOWithAuthorId("Fight Club", "DO NOT TALK ABOUT IT");

        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.of(book));
        when(authorRepository.findById(DEFAULT_AUTHOR_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(DEFAULT_BOOK_ID, formDTO))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void updateShouldReturnNotFound() {
        BookFormDTO formDTO = getBookFormDTOWithAuthorId("Fight Club", "DO NOT TALK ABOUT IT");
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(DEFAULT_BOOK_ID, formDTO))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void deleteByIdShouldBeCalled() {
        BookEntity book = getBookEntityWithAuthor("Fight Club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");

        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(DEFAULT_BOOK_ID);

        bookService.delete(DEFAULT_BOOK_ID);
        verify(bookRepository, times(1)).deleteById(DEFAULT_BOOK_ID);
    }

    @Test
    public void deleteByIdShouldReturnNotFound() {
        when(bookRepository.findById(DEFAULT_BOOK_ID)).thenReturn(Optional.empty());
        doNothing().when(bookRepository).deleteById(DEFAULT_BOOK_ID);

        assertThatThrownBy(() -> bookService.delete(DEFAULT_BOOK_ID))
                .isInstanceOf(ResponseStatusException.class);
    }
}
