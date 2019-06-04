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
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.impl.AuthorServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for Author Service")
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    @SpyBean
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Test invoke get author by id")
    void shouldGetAuthorById() {
        Mono<Author> authorMock = Mono.just(new Author("test", LocalDate.now(), "test"));
        when(authorRepository.findById(any(String.class))).thenReturn(authorMock);

        Mono<Author> author = authorService.getById("000");

        verify(authorRepository, times(1)).findById("000");
        assertEquals(authorMock, author);
    }

    @Test
    @DisplayName("Test invoke get all authors")
    void shouldGetAllAuthors() {
        authorService.getAll();
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test invoke delete author by id")
    void shouldDeleteAuthorById() {
        Book book = new Book(new Author("", null, ""),
                new Genre(""), "Book",
                LocalDate.now(), "russian",
                "Test", "Test", "555-555");

        when(bookService.deleteByAuthorId(any(String.class))).thenReturn(Flux.just(book));
        when(authorRepository.deleteById(any(String.class))).thenReturn(Mono.empty());

        authorService.deleteById("000");
        verify(authorRepository, times(1)).deleteById("000");
        verify(bookService, times(1)).deleteByAuthorId(any(String.class));
    }

    @Test
    @DisplayName("Test invoke insert new author")
    void shouldInsertNewAuthor() {
        Author author = new Author("test", LocalDate.now(),"test");
        when(authorRepository.save(author)).thenReturn(Mono.just(author));
        authorService.insert(author);
        verify(authorRepository, times(1)).insert(author);
    }
}