package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String>, PageableFindAll<Genre> {
    Flux<Genre> findByGenreName(String genreName);
}
