package com.joaoptgaino.bookstore.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;
import com.joaoptgaino.bookstore.dtos.book.BookParamsDTO;
import com.joaoptgaino.bookstore.fixtures.book.BookFormDTOFixture;
import com.joaoptgaino.bookstore.services.book.BookService;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.DEFAULT_AUTHOR_ID;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFixture.getAuthorDTO;
import static com.joaoptgaino.bookstore.fixtures.author.AuthorFormDTOFixture.getAuthorFormDTO;
import static com.joaoptgaino.bookstore.fixtures.book.BookFixture.DEFAULT_BOOK_ID;
import static com.joaoptgaino.bookstore.fixtures.book.BookFixture.getBookDTO;
import static com.joaoptgaino.bookstore.fixtures.book.BookFormDTOFixture.getBookFormDTO;
import static com.joaoptgaino.bookstore.fixtures.book.BookFormDTOFixture.getBookFormDTOWithAuthorId;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder().create();

    @Mock
    private BookService bookService;

    private static final String ENDPOINT_BOOK = "/books";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void saveBookShouldReturnSuccessful() throws Exception {
        BookDTO book = getBookDTO("Fight club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        BookFormDTO formDTO = getBookFormDTOWithAuthorId("Fight club", "Crazy dude with insomnia starts a fight club with his other personality");
        String bookRequest = gson.toJson(book);

        when(bookService.create(formDTO)).thenReturn(book);

        RequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_BOOK).contentType(MediaType.APPLICATION_JSON).content(bookRequest);
        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void findAllBooksShouldReturnSuccessful() throws Exception {
        BookDTO book = getBookDTO("Fight club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        Page<BookDTO> bookResponse = new PageImpl<>(List.of(book));
        BookParamsDTO paramsDTO = new BookParamsDTO();
        Pageable pageable = PageRequest.of(0, 10);

        when(bookService.findAll(paramsDTO, pageable)).thenReturn(bookResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_BOOK);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title")
                        .value("Fight club"));
    }

    @Test
    public void findOneShouldReturnSuccessful() throws Exception {
        BookDTO book = getBookDTO("Fight club", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");

        when(bookService.findOne(DEFAULT_BOOK_ID)).thenReturn(book);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", ENDPOINT_BOOK, DEFAULT_BOOK_ID))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(book.getTitle()));
    }

    @Test
    public void updateShouldReturnSuccessful() throws Exception {
        BookDTO updatedBook = getBookDTO("Fight club 2", "Crazy dude with insomnia starts a fight club with his other personality", "Chuck Palahniuk");
        BookFormDTO formDTO = getBookFormDTOWithAuthorId("Fight club", "Crazy dude with insomnia starts a fight club with his other personality");
        String bookRequest = gson.toJson(updatedBook);
        when(bookService.update(DEFAULT_BOOK_ID, formDTO)).thenReturn(updatedBook);

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", ENDPOINT_BOOK, DEFAULT_BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookRequest);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldSuccessful() throws Exception {
        doNothing().when(bookService).delete(DEFAULT_BOOK_ID);

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", ENDPOINT_BOOK, DEFAULT_BOOK_ID));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).delete(DEFAULT_BOOK_ID);
    }
}
