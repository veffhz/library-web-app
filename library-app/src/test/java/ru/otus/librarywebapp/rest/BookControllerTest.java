package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Mono;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.service.BookService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for book api")
@WebFluxTest(controllers = BookApi.class)
@Import(BookApi.class)
class BookControllerTest extends BaseTest {

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Test get all books on /api/book")
    void shouldGetAllBooks() {
        given(this.bookService.getAll(BookApi.BOOK_PAGE_REQUEST))
                .willReturn(Mono.just(new BookDto(Collections.singletonList(book()), 0, 1L)));

        String responseBody = "{\"books\":[{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null," +
                "\"lastName\":null,\"fullName\":\" \"},\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\"," +
                "\"publishDate\":\"2019-04-27\",\"language\":\"russian\",\"publishingHouse\":\"Test\",\"city\":\"Test\"," +
                "\"isbn\":\"555-555\"}],\"currentPage\":0,\"totalPages\":1}";

        this.webClient.get().uri(uriBuilder -> uriBuilder
                .path("/api/book")
                .queryParam("page", 0)
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test get book on /api/book/{123}")
    void shouldGetBook() {
        given(this.bookService.getById("123")).willReturn(Mono.just(book()));

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient.get().uri("/api/book/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test update book on /api/book")
    void shouldUpdateBook() {
        Book book = book();

        given(this.bookService.update(book)).willReturn(Mono.just(book()));

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient
                .mutateWith(csrf())
                .put().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.bookService, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("Test create book on post /api/book")
    void shouldCreateBook() {
        Book book = book();

        given(this.bookService.insert(book)).willReturn(Mono.just(book));

        String responseBody = "{\"id\":null,\"author\":{\"id\":null,\"firstName\":null,\"birthDate\":null,\"lastName\":null,\"fullName\":\" \"}," +
                "\"genre\":{\"id\":null,\"genreName\":null},\"bookName\":\"Best\",\"publishDate\":\"2019-04-27\",\"language\":\"russian\"," +
                "\"publishingHouse\":\"Test\",\"city\":\"Test\",\"isbn\":\"555-555\"}";

        this.webClient
                .mutateWith(csrf())
                .post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.bookService, times(1)).insert(any(Book.class));
    }

    @Test
    @DisplayName("Test delete book on /api/book/{id}")
    void shouldDeleteBookById() {
        given(this.bookService.deleteById("123")).willReturn(Mono.empty());

        this.webClient
                .mutateWith(csrf())
                .delete().uri("/api/book/123")
                .exchange()
                .expectStatus().isNoContent();

        verify(this.bookService, times(1)).deleteById("123");
    }

    private Book book() {
        return new Book(new Author(), new Genre(), "Best", date(), "russian",
                "Test", "Test", "555-555");
    }

}