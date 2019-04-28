package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.librarywebapp.domain.Book;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        long count = bookRepository.count();
        assertEquals(count, 2);
    }

    @Test
    @DisplayName("Test insert new book")
    void shouldInsertNewBook() {
        bookRepository.save(
                new Book(authorRepository.findAll().get(0), genreRepository.findAll().get(0),
                        "Best", LocalDate.now(), "russian",
                        "Test", "Test", "555-555"));
        assertEquals(bookRepository.count(), 3);
    }

    @Test
    @DisplayName("Test get book by id")
    void shouldGetBookById() {
        Book book = bookRepository.findAll().get(0);
        assertEquals(book.getBookName(), "Best7");
    }

    @Test
    @DisplayName("Test get book by name")
    void shouldGetBookByName() {
        List<Book> books = bookRepository.findByBookName("Best");
        assertEquals(books.size(), 1);

        Book book = books.get(0);
        assertEquals(book.getBookName(), "Best");
    }

    @Test
    @DisplayName("Test get books by part name")
    void shouldGetBooksByPartName() {
        List<Book> books = bookRepository.findByBookNameContaining("est");
        assertEquals(books.size(), 3);
    }

    @Test
    @DisplayName("Test get all books")
    void shouldGetAllBooks() {
        List<Book> books = bookRepository.findAll();
        Book book = books.get(0);

        assertEquals(book.getBookName(), "Best7");

        book = books.get(1);

        assertEquals(book.getBookName(), "Best9");
    }

    @Test
    @DisplayName("Test delete book by id")
    void shouldDeleteBookById() {
        Book book = bookRepository.findAll().get(0);
        bookRepository.deleteById(book.getId());
        assertEquals(bookRepository.count(), 2);
    }

    @Test
    @DisplayName("Test delete book")
    void shouldDeleteBook() {
        Book book = bookRepository.findAll().get(0);
        bookRepository.delete(book);
        assertEquals(bookRepository.count(), 2);
    }
}