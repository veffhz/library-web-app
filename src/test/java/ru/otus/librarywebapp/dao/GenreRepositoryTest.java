package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarywebapp.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test for GenreRepository")
@DataMongoTest
@ComponentScan({"ru.otus.librarywebapp.dao", "ru.otus.librarywebapp.testconfig"})
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Test return count genres")
    void shouldReturnCorrectCount() {
        long count = genreRepository.count();
        assertEquals(count, 2);
    }

    @Test
    @DisplayName("Test insert new genre")
    void shouldInsertNewGenre() {
        genreRepository.save(new Genre("test"));
        assertEquals(genreRepository.count(), 3);
    }

    @Test
    @DisplayName("Test get genre by id")
    void shouldGetGenreById() {
        Genre genre = genreRepository.findAll().get(0);
        assertEquals(genre.getGenreName(), "Genre");
    }

    @Test
    @DisplayName("Test get all genre")
    void shouldGetAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        Genre genre = genres.get(0);

        assertEquals(genre.getGenreName(), "Genre7");

        genre = genres.get(1);

        assertEquals(genre.getGenreName(), "Genre9");
    }

    @Test
    @DisplayName("Test delete genre by id")
    void shouldDeleteGenreById() {
        Genre genre = genreRepository.findAll().get(0);
        genreRepository.deleteById(genre.getId());
        assertEquals(genreRepository.count(), 2);
    }

    @Test
    @DisplayName("Test delete genre")
    void shouldDeleteGenre() {
        Genre genre = genreRepository.findAll().get(0);
        genreRepository.delete(genre);
        assertEquals(genreRepository.count(), 3);
    }
}