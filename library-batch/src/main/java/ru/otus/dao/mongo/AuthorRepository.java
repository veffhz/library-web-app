package ru.otus.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
