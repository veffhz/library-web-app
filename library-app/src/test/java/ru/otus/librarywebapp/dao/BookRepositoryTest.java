package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test for BookRepository")
@DataMongoTest
@ComponentScan({"ru.otus.librarywebapp.dao", "ru.otus.librarywebapp.testconfig"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Test return count books")
    void shouldReturnCorrectCount() {
        Mono<Long> count = bookRepository.count();

        StepVerifier
                .create(count)
                .assertNext(c -> assertEquals(c.longValue(), 2))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test insert new book")
    void shouldInsertNewBook() {
        Book book = new Book(
                authorRepository.findAll().blockFirst(),
                genreRepository.findAll().blockFirst(),
                "Best", LocalDate.now(), "russian",
                "Test", "Test", "555-555"
        );

        StepVerifier
                .create(bookRepository.save(book))
                .assertNext(b -> assertNotNull(b.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get book by id")
    void shouldGetBookById() {
        Mono<Book> book = bookRepository.findAll().elementAt(0);

        StepVerifier
                .create(book)
                .assertNext(b -> assertEquals(b.getBookName(), "Best7"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get book by name")
    void shouldGetBookByName() {
        Flux<Book> books = bookRepository.findByBookName("Best");

        StepVerifier
                .create(books)
                .assertNext(b -> assertEquals(b.getBookName(), "Best"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get book by authorId")
    void shouldGetBookByAuthorId() {
        Author author = authorRepository.findAll().blockFirst();
        Objects.requireNonNull(author);

        Flux<Book> books = bookRepository.findByAuthorId(author.getId());

        StepVerifier
                .create(books)
                .assertNext(b -> assertEquals(b.getBookName(), "Best"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get book by genreId")
    void shouldGetBookByGenreId() {
        Genre genre = genreRepository.findAll().blockFirst();
        Objects.requireNonNull(genre);

        Flux<Book> books = bookRepository.findByGenreId(genre.getId());

        StepVerifier
                .create(books)
                .assertNext(b -> assertEquals(b.getBookName(), "Best"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get books by part name")
    void shouldGetBooksByPartName() {
        Flux<Book> books = bookRepository.findByBookNameContaining("est");

        StepVerifier
                .create(books)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get all books")
    void shouldGetAllBooks() {
        Flux<Book> books = bookRepository.findAll();

        StepVerifier
                .create(books)
                .assertNext(b -> assertEquals(b.getBookName(), "Best7"))
                .assertNext(b -> assertEquals(b.getBookName(), "Best9"))
                .assertNext(b -> assertEquals(b.getBookName(), "Best"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test delete book by id")
    void shouldDeleteBookById() {
        Book book = bookRepository.findAll().blockFirst();

        Objects.requireNonNull(book);

        StepVerifier.create(bookRepository.deleteById(book.getId()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test delete book")
    void shouldDeleteBook() {
        Book book = bookRepository.findAll().blockFirst();

        Objects.requireNonNull(book);

        StepVerifier.create(bookRepository.delete(book))
                .verifyComplete();
    }
}