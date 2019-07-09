package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

public interface GenreService {
    Mono<Long> count();
    Mono<Genre> getById(String id);
    Flux<Genre> getByGenreName(String genreName);
    Flux<Genre> getAll();
    Mono<GenreDto> getAll(Pageable pageable);
    Mono<Void> deleteById(String id);
    Mono<Genre> insert(Genre genre);
    Mono<Genre> update(Genre genre);
}
