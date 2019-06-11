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

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test for AuthorRepository")
@DataMongoTest
@ComponentScan({"ru.otus.librarywebapp.dao", "ru.otus.librarywebapp.testconfig"})
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Test return count authors")
    void shouldReturnCorrectCount() {
        Mono<Long> count = authorRepository.count();

        StepVerifier
                .create(count)
                .assertNext(c -> assertEquals(c.longValue(), 2))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test insert new author")
    void shouldInsertNewAuthor() {
        Author author = new Author("test", LocalDate.now(), "test");

        StepVerifier
                .create(authorRepository.save(author))
                .assertNext(a -> assertNotNull(a.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get author by id")
    void shouldGetAuthorById() {
        Mono<Author> author = authorRepository.findById("5");

        StepVerifier
                .create(author)
                .assertNext(a -> assertEquals(a.getFirstName(), "FirstName"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get author by last name")
    void shouldGetAuthorsByLastName() {
        Flux<Author> authors = authorRepository.findByLastName("LastName");

        StepVerifier
                .create(authors)
                .assertNext(b -> assertEquals(b.getFirstName(), "FirstName"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test get all authors")
    void shouldGetAllAuthors() {
        Flux<Author> authors = authorRepository.findAll();

        StepVerifier
                .create(authors)
                .assertNext(b -> assertEquals(b.getFirstName(), "FirstName"))
                .assertNext(b -> assertEquals(b.getFirstName(), "FirstName7"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Test delete author by id")
    void shouldDeleteAuthorById() {
        StepVerifier.create(authorRepository.deleteById("9"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test delete author")
    void shouldDeleteAuthor() {
        Author author = authorRepository.findById("7").block();

        Objects.requireNonNull(author);

        StepVerifier.create(authorRepository.delete(author))
                .verifyComplete();
    }
}