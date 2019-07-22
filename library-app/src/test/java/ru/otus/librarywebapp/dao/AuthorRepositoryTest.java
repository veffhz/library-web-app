package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.domain.Author;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
        long count = authorRepository.count();
        assertEquals(count, 2);
    }

    @Test
    @DisplayName("Test insert new author")
    void shouldInsertNewAuthor() {
        Author author = new Author("test", LocalDate.now(), "test");
        authorRepository.save(author);
        assertNotNull(author.getId());
    }

    @Test
    @DisplayName("Test get author by id")
    void shouldGetAuthorById() {
        Author author = authorRepository.findById("5").get();
        assertEquals(author.getFirstName(), "FirstName");
    }

    @Test
    @DisplayName("Test get author by last name")
    void shouldGetAuthorsByLastName() {
        List<Author> authors = authorRepository.findByLastName("LastName");
        assertEquals(authors.iterator().next().getFirstName(), "FirstName");
    }

    @Test
    @DisplayName("Test get all authors")
    void shouldGetAllAuthors() {
        Iterator<Author> iterator = authorRepository.findAll().iterator();

        assertEquals(iterator.next().getFirstName(), "FirstName");
        assertEquals(iterator.next().getFirstName(), "FirstName7");
    }

    @Test
    @DisplayName("Test delete author by id")
    void shouldDeleteAuthorById() {
        authorRepository.deleteById("9");
        assertEquals(authorRepository.count(), 2);
    }

    @Test
    @DisplayName("Test delete author")
    void shouldDeleteAuthor() {
        Author author = authorRepository.findById("7").get();
        authorRepository.delete(author);
        assertEquals(authorRepository.count(), 2);
    }
}