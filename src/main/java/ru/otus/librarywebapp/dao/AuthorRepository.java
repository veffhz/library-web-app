package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.librarywebapp.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByLastName(String lastName);
}
