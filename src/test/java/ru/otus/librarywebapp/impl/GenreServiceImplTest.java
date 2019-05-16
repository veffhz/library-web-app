package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.impl.GenreServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for Genre Service")
@ExtendWith(SpringExtension.class)
class GenreServiceImplTest {

    @SpyBean
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Test invoke get genre by id")
    void shouldGetGenreById() {
        Mono<Genre> genreMock = Mono.just(new Genre("test"));
        when(genreRepository.findById(any(String.class))).thenReturn(genreMock);

        Mono<Genre> genre = genreService.getById("000");

        verify(genreRepository, times(1)).findById("000");
        assertEquals(genreMock, genre);
    }

    @Test
    @DisplayName("Test invoke get all genres")
    void shouldGetAllGenres() {
        genreService.getAll();
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test invoke delete genre by id")
    void shouldDeleteGenreById() {
        genreService.deleteById("000");
        verify(genreRepository, times(1)).deleteById("000");
    }

    @Test
    @DisplayName("Test invoke insert new genre")
    void shouldInsertNewGenre() {
        Genre genre = new Genre("test");
        genreService.insert(genre);
        verify(genreRepository, times(1)).insert(genre);
    }
}