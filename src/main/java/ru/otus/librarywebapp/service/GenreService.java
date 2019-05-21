package ru.otus.librarywebapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Genre;

public interface GenreService {
    Mono<Long> count();
    Mono<Genre> getById(String id);
    Flux<Genre> getByGenreName(String genreName);
    Flux<Genre> getAll();
    Mono<Void> deleteById(String id);
    Mono<Genre> insert(Genre genre);
    Mono<Genre> update(Genre genre);
}
