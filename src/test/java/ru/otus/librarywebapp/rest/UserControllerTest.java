package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for user api")
@WebFluxTest(controllers = UserApi.class)
@Import(UserApi.class)
class UserControllerTest extends BaseTest {

    @Test
    @DisplayName("Test get on /api/user")
    void shouldGetUser() {
        String responseBody = "[\"usr\",null,{\"password\":\"password\",\"username\":\"usr\"," +
                "\"authorities\":[{\"authority\":\"ROLE_USER\"}],\"accountNonExpired\":true,\"accountNonLocked\":true," +
                "\"credentialsNonExpired\":true,\"enabled\":true},[{\"authority\":\"ROLE_USER\"}]]";

        this.webClient.get().uri("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test post on /api/user")
    void shouldPostUser() {
        String responseBody = "[\"usr\",[{\"authority\":\"ROLE_USER\"}],\"Test\"]";

        this.webClient
                .mutateWith(csrf())
                .post().uri("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject("Test"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo(responseBody);
    }

}