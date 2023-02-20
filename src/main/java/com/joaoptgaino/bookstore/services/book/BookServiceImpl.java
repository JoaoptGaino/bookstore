package com.joaoptgaino.bookstore.services.book;

import com.joaoptgaino.bookstore.dtos.book.BookDTO;
import com.joaoptgaino.bookstore.dtos.book.BookFormDTO;
import com.joaoptgaino.bookstore.dtos.book.BookParamsDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.entities.BookEntity;
import com.joaoptgaino.bookstore.repositories.AuthorRepository;
import com.joaoptgaino.bookstore.repositories.BookRepository;
import com.joaoptgaino.bookstore.repositories.specifications.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDTO create(BookFormDTO data) {
        BookEntity book = modelMapper.map(data, BookEntity.class);
        bookRepository.save(book);

        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public Page<BookDTO> findAll(BookParamsDTO paramsDTO, Pageable pageable) {
        Specification<BookEntity> specification = BookSpecification.create(paramsDTO);
        Page<BookEntity> books = bookRepository.findAll(specification, pageable);
        return new PageImpl<>(books.getContent().stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList()));

    }

    @Override
    public BookDTO findOne(UUID id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Book not found"));

        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO update(UUID id, BookFormDTO data) {
        BookEntity book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Book not found"));
        AuthorEntity authorEntity = checkAuthor(data.getAuthorId(), book);

        Date date = new Date();

        book.setId(id);
        book.setTitle(data.getTitle());
        book.setSummary(data.getSummary());
        book.setAuthor(authorEntity);
        book.setUpdatedAt(date);

        bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public void delete(UUID id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Book not found"));
        bookRepository.deleteById(id);
    }

    private AuthorEntity checkAuthor(UUID authorId, BookEntity book) {
        if (authorId != null) {
            return authorRepository.findById(authorId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Author not found"));
        }
        return book.getAuthor();
    }
}
