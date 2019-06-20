package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.dao.GenreRepository;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import ru.otus.librarywebapp.service.impl.BookServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for Book Service")
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @SpyBean
    private BookServiceImpl bookService;

    //@MockBean
    //private AuthorRepository authorRepository;

    //@MockBean
    //private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Test invoke get book by id")
    void shouldGetBookById() {
        Mono<Book> bookMock = Mono.just(new Book(new Author("", null, ""),
                new Genre(""), "Book",
                LocalDate.now(), "russian",
                "Test", "Test", "555-555"));
        when(bookRepository.findById(any(String.class))).thenReturn(bookMock);

        Mono<Book> book = bookService.getById("000");

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
        Comment comment = new Comment("test", LocalDateTime.now(), "test");

        when(commentRepository.deleteByBookId(any(String.class))).thenReturn(Flux.just(comment));
        when(bookRepository.deleteById(any(String.class))).thenReturn(Mono.empty());

        bookService.deleteById("000");
        verify(commentRepository, times(1)).deleteByBookId("000");
        verify(bookRepository, times(1)).deleteById("000");
    }

    @Test
    @DisplayName("Test invoke insert new book")
    void shouldInsertNewBook() {
        Book book = new Book(new Author("", null, ""),
                new Genre(""), "Book",
                LocalDate.now(), "russian",
                "Test", "Test", "555-555");
        bookService.insert(book);
        verify(bookRepository, times(1)).insert(book);
    }
}