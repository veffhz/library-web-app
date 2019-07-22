package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
        long count = bookRepository.count();
        assertEquals(count, 2);
    }

    @Test
    @DisplayName("Test insert new book")
    void shouldInsertNewBook() {
        Book book = new Book(
                authorRepository.findAll().iterator().next(),
                genreRepository.findAll().iterator().next(),
                "Best", LocalDate.now(), "russian",
                "Test", "Test", "555-555"
        );
        bookRepository.save(book);
        assertNotNull(book.getId());
    }

    @Test
    @DisplayName("Test get book by name")
    void shouldGetBookByName() {
        List<Book> books = bookRepository.findByBookName("Best");
        assertEquals(books.iterator().next().getBookName(), "Best");
    }

    @Test
    @DisplayName("Test get book by authorId")
    void shouldGetBookByAuthorId() {
        Author author = authorRepository.findAll().get(0);
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertEquals(books.iterator().next().getBookName(), "Best");
    }

    @Test
    @DisplayName("Test get book by genreId")
    void shouldGetBookByGenreId() {
        Genre genre = genreRepository.findAll().get(0);
        List<Book> books = bookRepository.findByGenreId(genre.getId());
        assertEquals(books.iterator().next().getBookName(), "Best");
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
        Iterator<Book> iterator = bookRepository.findAll().iterator();
        assertEquals(iterator.next().getBookName(), "Best7");
        assertEquals(iterator.next().getBookName(), "Best9");
        assertEquals(iterator.next().getBookName(), "Best");
    }

    @Test
    @DisplayName("Test delete book by id")
    void shouldDeleteBookById() {
        Book book = bookRepository.findAll().get(0);
        bookRepository.deleteById(book.getId());
        assertEquals(authorRepository.count(), 2);
    }

    @Test
    @DisplayName("Test delete book")
    void shouldDeleteBook() {
        Book book = bookRepository.findAll().get(0);
        bookRepository.deleteById(book.getId());
        assertEquals(authorRepository.count(), 2);
    }
}