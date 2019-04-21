package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.impl.AuthorServiceImpl;

import java.util.Date;
import java.util.Optional;

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
    private BookRepository bookRepository;

    @Test
    @DisplayName("Test invoke get author by id")
    void shouldGetAuthorById() {
        Author authorMock = new Author("test", new Date(), "test");
        when(authorRepository.findById(any(String.class))).thenReturn(Optional.of(authorMock));

        Author author = authorService.getById("000").get();

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
        authorService.deleteById("000");
        verify(authorRepository, times(1)).deleteById("000");
    }

    @Test
    @DisplayName("Test invoke insert new author")
    void shouldInsertNewAuthor() {
        Date date = new Date();
        Author author = new Author("test", date,"test");
        when(authorRepository.save(author)).thenReturn(author);
        authorService.insert("test", date, "test");
        verify(authorRepository, times(1)).save(author);
    }
}