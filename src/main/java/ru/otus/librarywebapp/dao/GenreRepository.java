package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.librarywebapp.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByGenreName(String genreName);
}
