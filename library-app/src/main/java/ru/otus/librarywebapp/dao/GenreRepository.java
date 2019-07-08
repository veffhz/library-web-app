package ru.otus.librarywebapp.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import ru.otus.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Flux<Genre> findByGenreName(String genreName);
    @Query("{ id: { $exists: true }}")
    Flux<Genre> findAll(Pageable pageable);
}
