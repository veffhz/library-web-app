package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.service.BookService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for book api")
@WebMvcTest(controllers = BookApi.class)
@Import(BookApi.class)
class BookControllerTest extends BaseTest {

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Test get all books on /api/book")
    void shouldGetAllBooks() throws Exception {
        given(this.bookService.getAll(BookApi.BOOK_PAGE_REQUEST))
                .willReturn(new BookDto(Collections.singletonList(book()), 0, 1L));

        String responseBody = "{\"books\":[{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null," +
                "\"lastName\":null,\"fullName\":\" \"},\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\"," +
                "\"publishDate\":\"2019-04-27\",\"language\":\"russian\",\"publishingHouse\":\"Test\",\"city\":\"Test\"," +
                "\"isbn\":\"555-555\"}],\"currentPage\":0,\"totalPages\":1}";

        this.webClient.perform(get("/api/book").param("page", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get book on /api/book/{123}")
    void shouldGetBook() throws Exception {
        given(this.bookService.getById("123")).willReturn(Optional.of(book()));

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient.perform(get("/api/book/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update book on /api/book")
    void shouldUpdateBook() throws Exception {
        Book book = book();

        given(this.bookService.update(book)).willReturn(book());

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient.perform(put("/api/book").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.bookService, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("Test create book on post /api/book")
    void shouldCreateBook() throws Exception {
        Book book = book();

        given(this.bookService.insert(book)).willReturn(book);

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient.perform(post("/api/book").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));

        verify(this.bookService, times(1)).insert(any(Book.class));
    }

    @Test
    @DisplayName("Test delete book on /api/book/{id}")
    void shouldDeleteBookById() throws Exception {
        this.webClient.perform(delete("/api/book/123").with(csrf()))
                .andExpect(status().isNoContent());

        verify(this.bookService, times(1)).deleteById("123");
    }

    private Book book() {
        return new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");
    }

}