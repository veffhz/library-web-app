package ru.otus.librarywebapp.service;

import ru.otus.librarywebapp.domain.Author;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    long count();
    Optional<Author> getById(String id);
    List<Author> getByLastName(String name);
    List<Author> getAll();
    void deleteById(String id);
    String insert(String firstName, Date birthDate, String lastName);
    Author insert(Author author);
    Author update(Author author);
}
