package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    long count();
    Optional<Author> getById(String id);
    List<Author> getByLastName(String name);
    List<Author> getAll();
    AuthorDto getAll(Pageable pageable);
    void deleteById(String id);
    Author insert(Author author);
    Author update(Author author);
}
