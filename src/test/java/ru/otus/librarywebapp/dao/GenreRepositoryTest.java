package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import ru.otus.librarywebapp.domain.Genre;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test for GenreRepository")
@DataMongoTest
@ComponentScan({"ru.otus.librarywebapp.dao", "ru.otus.librarywebapp.testconfig"})
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Test return count genres")
    void shouldReturnCorrectCount() {
        Mono<Long> count = genreRepository.count();

        StepVerifier
                .create(count)
                .assertNext(c -> assertEquals(c.longValue(), 2))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test insert new genre")
    void shouldInsertNewGenre() {
        Genre genre = new Genre("test");

        StepVerifier
                .create(genreRepository.save(genre))
                .assertNext(g -> assertNotNull(g.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get genre by id")
    void shouldGetGenreById() {
        Mono<Genre> genre = genreRepository.findAll().elementAt(0);

        StepVerifier
                .create(genre)
                .assertNext(g -> assertEquals(g.getGenreName(), "Genre"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get all genre")
    void shouldGetAllGenres() {
        Flux<Genre> genres = genreRepository.findAll();

        StepVerifier
                .create(genres)
                .assertNext(g -> assertEquals(g.getGenreName(), "Genre7"))
                .assertNext(g -> assertEquals(g.getGenreName(), "Genre9"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test delete genre by id")
    void shouldDeleteGenreById() {
        Genre genre = genreRepository.findAll().blockFirst();

        Objects.requireNonNull(genre);

        StepVerifier.create(genreRepository.deleteById(genre.getId()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test delete genre")
    void shouldDeleteGenre() {
        Genre genre = genreRepository.findAll().blockFirst();

        Objects.requireNonNull(genre);

        StepVerifier.create(genreRepository.delete(genre))
                .verifyComplete();
    }
}