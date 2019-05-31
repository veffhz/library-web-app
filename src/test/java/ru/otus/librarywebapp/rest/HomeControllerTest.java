package ru.otus.librarywebapp.rest;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

import ru.otus.librarywebapp.controller.HomeController;
import ru.otus.librarywebapp.domain.*;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import static org.mockito.BDDMockito.given;

@DisplayName("Test for Home Controller")
@WebFluxTest(controllers = HomeController.class)
@Import(HomeController.class)
class HomeControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test redirect on / ")
    public void shouldRedirectToLogin() {
        this.webClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("Test get info page on / ")
    @WithMockUser
    void shouldGetInfoPage() {
        given(this.authorService.getAll()).willReturn(Flux.just(new Author()));
        given(this.genreService.getAll()).willReturn(Flux.just(new Genre()));
        given(this.bookService.getAll()).willReturn(Flux.just(new Book()));
        given(this.commentService.getAll()).willReturn(Flux.just(new Comment()));

        this.webClient.get().uri("/")
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(
                response -> Assertions.assertThat(response.getResponseBody()).contains("frontendData")
        );
    }

}