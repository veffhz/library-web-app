package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.librarywebapp.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Flux<Genre> findByGenreName(String genreName);
}
