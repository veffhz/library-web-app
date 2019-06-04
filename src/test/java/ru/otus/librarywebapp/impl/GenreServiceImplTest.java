package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.impl.GenreServiceImpl;

import java.time.LocalDate;

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

    @MockBean
    private BookService bookService;

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
        Book book = new Book(new Author("", null, ""),
                new Genre(""), "Book",
                LocalDate.now(), "russian",
                "Test", "Test", "555-555");

        when(bookService.deleteByGenreId(any(String.class))).thenReturn(Flux.just(book));
        when(genreRepository.deleteById(any(String.class))).thenReturn(Mono.empty());

        genreService.deleteById("000");
        verify(genreRepository, times(1)).deleteById("000");
        verify(bookService, times(1)).deleteByGenreId(any(String.class));
    }

    @Test
    @DisplayName("Test invoke insert new genre")
    void shouldInsertNewGenre() {
        Genre genre = new Genre("test");
        genreService.insert(genre);
        verify(genreRepository, times(1)).insert(genre);
    }
}