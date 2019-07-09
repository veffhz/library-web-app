package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String>, PageableFindAll<Author> {
    Flux<Author> findByLastName(String lastName);
}
