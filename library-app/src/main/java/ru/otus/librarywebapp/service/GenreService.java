package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    long count();
    Optional<Genre> getById(String id);
    List<Genre> getByGenreName(String genreName);
    List<Genre> getAll();
    GenreDto getAll(Pageable pageable);
    void deleteById(String id);
    Genre insert(Genre genre);
    Genre update(Genre genre);
}
