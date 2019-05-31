package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.GenreService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for genre api")
@WebFluxTest(controllers = GenreApi.class)
@Import(GenreApi.class)
class GenreControllerTest extends BaseTest {

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Test get genres on /api/genre")
    void shouldGetAllGenres() {
        given(this.genreService.getAll()).willReturn(Flux.just(genre(), genre()));

        String responseBody = "[{\"id\":null,\"genreName\":\"test\"},{\"id\":null,\"genreName\":\"test\"}]";

        this.webClient.get().uri("/api/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test get genre on /api/genre/{id}")
    void shouldGetGenre() {
        given(this.genreService.getById("123")).willReturn(Mono.just(genre()));

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient.get().uri("/api/genre/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test update genre on /api/genre")
    void shouldUpdateGenre() {
        Genre genre = genre();

        given(this.genreService.update(genre)).willReturn(Mono.just(genre()));

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient
                .mutateWith(csrf())
                .put().uri("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(genre))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.genreService, times(1)).update(any(Genre.class));
    }

    @Test
    @DisplayName("Test create genre on post /api/genre")
    void shouldCreateGenre() {
        Genre genre = genre();

        given(this.genreService.insert(genre)).willReturn(Mono.just(genre()));

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient
                .mutateWith(csrf())
                .post().uri("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(genre))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.genreService, times(1)).insert(any(Genre.class));
    }

    @Test
    @DisplayName("Test delete genre on /api/genre/{id}")
    void shouldDeleteGenreById() {
        given(this.genreService.deleteById("123")).willReturn(Mono.empty());

        this.webClient
                .mutateWith(csrf())
                .delete().uri("/api/genre/123")
                .exchange()
                .expectStatus().isNoContent();

        verify(this.genreService, times(1)).deleteById("123");
    }

    private Genre genre() {
        return new Genre("test");
    }

}