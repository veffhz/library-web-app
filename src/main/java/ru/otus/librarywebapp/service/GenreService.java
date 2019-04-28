package ru.otus.librarywebapp.service;

import ru.otus.librarywebapp.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    long count();
    Optional<Genre> getById(String id);
    List<Genre> getByGenreName(String genreName);
    List<Genre> getAll();
    void deleteById(String id);
    Genre insert(Genre genre);
    Genre update(Genre genre);
}
