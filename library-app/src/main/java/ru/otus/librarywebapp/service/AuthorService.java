package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;

public interface AuthorService {
    Mono<Long> count();
    Mono<Author> getById(String id);
    Flux<Author> getByLastName(String name);
    Flux<Author> getAll();
    Mono<AuthorDto> getAll(Pageable pageable);
    Mono<Void> deleteById(String id);
    Mono<Author> insert(Author author);
    Mono<Author> update(Author author);
}
