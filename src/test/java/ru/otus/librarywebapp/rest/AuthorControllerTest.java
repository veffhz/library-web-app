package ru.otus.librarywebapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;
import ru.otus.librarywebapp.utils.Helper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("Test for author Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthorApi.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    //Все бины нужны, потому что иначе spring начинает искать mongoTemplate bean, и не находит
    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreService genreService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test get all authors page on /api/author")
    void shouldGetAllAuthors() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        List<Author> authors = Arrays.asList(
                new Author("test", date, "test"),
                new Author("test", date, "test"));

        given(this.authorService.getAll()).willReturn(authors);

        String responseBody = "[{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}," +
                "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}]";

        this.mvc.perform(get("/api/author")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get author on /api/author/{id}")
    void shouldGetAuthor() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Author author = new Author("test", date, "test");

        given(this.authorService.getById("123")).willReturn(Optional.of(author));

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.mvc.perform(get("/api/author/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update author on /api/author/{id}")
    void shouldUpdateAuthor() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Author author = new Author("test", date, "test");

        given(this.authorService.update(author)).willReturn(author);

        ObjectMapper mapper = new ObjectMapper();

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.mvc.perform(put("/api/author/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.authorService, times(1)).update(any(Author.class));
    }

    @Test
    @DisplayName("Test create author on post /api/author")
    void shouldCreateAuthor() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Author author = new Author("test", date, "test");
        given(this.authorService.insert(author)).willReturn(author);

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        ObjectMapper mapper = new ObjectMapper();

        this.mvc.perform(post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));
        verify(this.authorService, times(1)).insert(any(Author.class));
    }

    @Test
    @DisplayName("Test delete author on /api/author/{id}")
    void shouldDeleteAuthorById() throws Exception {
        this.mvc.perform(delete("/api/author/123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());
        verify(this.authorService, times(1)).deleteById("123");
    }

}