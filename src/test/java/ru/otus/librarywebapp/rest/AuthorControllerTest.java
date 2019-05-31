package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.AuthorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for author api")
@WebFluxTest(controllers = AuthorApi.class)
@Import(AuthorApi.class)
class AuthorControllerTest extends BaseTest {

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Test get all authors page on /api/author")
    void shouldGetAllAuthors() {
        given(this.authorService.getAll()).willReturn(Flux.just(author(), author()));

        String responseBody = "[{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}," +
                "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}]";

        this.webClient.get().uri("/api/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test get author on /api/author/{id}")
    void shouldGetAuthor() {
        given(this.authorService.getById("123")).willReturn(Mono.just(author()));

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient.get().uri("/api/author/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test update author on /api/author")
    void shouldUpdateAuthor() {
        Author author = author();

        given(this.authorService.update(author)).willReturn(Mono.just(author));

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient
                .mutateWith(csrf())
                .put().uri("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(author))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.authorService, times(1)).update(any(Author.class));
    }

    @Test
    @DisplayName("Test create author on post /api/author")
    void shouldCreateAuthor() {
        Author author = author();

        given(this.authorService.insert(author)).willReturn(Mono.just(author));

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient
                .mutateWith(csrf())
                .post().uri("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(author))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.authorService, times(1)).insert(any(Author.class));
    }

    @Test
    @DisplayName("Test delete author on /api/author/{id}")
    @WithMockUser(username = "adm", authorities = "ROLE_ADMIN") // TODO check admin
    void shouldDeleteAuthorById() {
        given(this.authorService.deleteById("123")).willReturn(Mono.empty());

        this.webClient
                .mutateWith(csrf())
                .delete().uri("/api/author/123")
                .exchange()
                .expectStatus().isNoContent();

        verify(this.authorService, times(1)).deleteById("123");
    }

    private Author author() {
        return new Author("test", date(), "test");
    }

}