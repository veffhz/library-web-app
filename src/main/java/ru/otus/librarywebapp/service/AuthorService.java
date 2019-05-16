package ru.otus.librarywebapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Author;

public interface AuthorService {
    Mono<Long> count();
    Mono<Author> getById(String id);
    Flux<Author> getByLastName(String name);
    Flux<Author> getAll();
    Mono<Void> deleteById(String id);
    Mono<Author> insert(Author author);
    Mono<Author> update(Author author);
}
