package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.impl.BookServiceImpl;
import ru.otus.librarywebapp.service.impl.GenreServiceImpl;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for Book Service")
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @SpyBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Test invoke get book by id")
    void shouldGetBookById() {
        Book bookMock = new Book(new Author("", null, ""),
                new Genre(""), "Book",
                new Date(), "russian",
                "Test", "Test", "555-555");
        when(bookRepository.findById(any(String.class))).thenReturn(Optional.of(bookMock));

        Book book = bookService.getById("000").get();

        verify(bookRepository, times(1)).findById("000");
        assertEquals(bookMock, book);
    }

    @Test
    @DisplayName("Test invoke get all books")
    void shouldGetAllBooks() {
        bookService.getAll();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test invoke delete book by id")
    void shouldDeleteBookById() {
        bookService.deleteById("000");
        verify(bookRepository, times(1)).deleteById("000");
    }

    @Test
    @DisplayName("Test invoke insert new book")
    void shouldInsertNewBook() {
        when(authorRepository.findById("000")).thenReturn(Optional.of(new Author()));
        when(genreService.getById("000")).thenReturn(Optional.of(new Genre()));

        bookService.insert("000", "000", "Book",
                "1901-01-01", "russian",
                "Test", "Test", "555-555");
        verify(bookRepository, times(1)).save(ArgumentMatchers.any());
    }
}