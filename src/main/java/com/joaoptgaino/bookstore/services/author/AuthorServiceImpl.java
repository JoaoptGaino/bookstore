package com.joaoptgaino.bookstore.services.author;

import com.joaoptgaino.bookstore.dtos.author.AuthorDTO;
import com.joaoptgaino.bookstore.dtos.author.AuthorFormDTO;
import com.joaoptgaino.bookstore.entities.AuthorEntity;
import com.joaoptgaino.bookstore.repositories.AuthorRepository;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public AuthorDTO create(AuthorFormDTO data) {
        AuthorEntity author = modelMapper.map(data, AuthorEntity.class);
        authorRepository.save(author);

        return modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public Page<AuthorDTO> findAll(Pageable pageable) {
        Page<AuthorEntity> authors = authorRepository.findAll(pageable);
        return new PageImpl<>(authors.getContent().stream().map(author -> modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public AuthorDTO findOne(UUID id) {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Author not found"));
        return modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public AuthorDTO update(UUID id, AuthorFormDTO data) {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Author not found"));
        Date date = new Date();
        author.setId(id);
        author.setName(data.getName());
        author.setUpdatedAt(date);

        return modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public void delete(UUID id) {
        authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Author not found"));
        authorRepository.deleteById(id);
    }
}
