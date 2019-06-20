package ru.otus.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findOneByGenreName(String genreName);
}
