package ru.otus.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByLastName(String lastName);
}
