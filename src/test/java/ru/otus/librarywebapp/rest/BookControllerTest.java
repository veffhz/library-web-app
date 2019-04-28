package ru.otus.librarywebapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.BookService;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for book Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookApi.class)
@Import(BookApi.class)
class BookControllerTest {

    private static final String DATE = "2019-04-27";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Test get all books on /api/book")
    void shouldGetAllBooks() throws Exception {
        Book book = new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");

        List<Book> books = Collections.singletonList(book);

        given(this.bookService.getAll()).willReturn(books);

        String responseBody = "[{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}]";

        this.mvc.perform(get("/api/book")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get book on /api/book/{123}")
    void shouldGetBook() throws Exception {
        Book book = new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");

        given(this.bookService.getById("123")).willReturn(Optional.of(book));

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.mvc.perform(get("/api/book/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update book on /api/book")
    void shouldUpdateBook() throws Exception {
        Book book = new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");

        given(this.bookService.update(book)).willReturn(book);

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.mvc.perform(put("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.bookService, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("Test create book on post /api/book")
    void shouldCreateBook() throws Exception {
        Book book = new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");

        given(this.bookService.insert(book)).willReturn(book);

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));
        verify(this.bookService, times(1)).insert(any(Book.class));
    }

    @Test
    @DisplayName("Test delete book on /api/book/{id}")
    void shouldDeleteBookById() throws Exception {
        this.mvc.perform(delete("/api/book/123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());
        verify(this.bookService, times(1)).deleteById("123");
    }

    private LocalDate date(){
        return mapper.convertValue(DATE, LocalDate.class);
    }

}