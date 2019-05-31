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

import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for comment api")
@WebFluxTest(controllers = CommentApi.class)
@Import(CommentApi.class)
class CommentControllerTest extends BaseTest {

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test get all comments on /api/comment")
    void shouldGetAllComments() {
        given(this.commentService.getAll()).willReturn(Flux.just(comment()));

        String responseBody = "[{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}]";

        this.webClient.get().uri("/api/comment")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test get comment on /api/comment{id}")
    void shouldGetComment() {
        given(this.commentService.getById("123")).willReturn(Mono.just(comment()));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient.get().uri("/api/comment/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    @DisplayName("Test update comment page on /api/comment")
    void shouldUpdateComment() {
        Comment comment = comment();

        given(this.commentService.update(any(Comment.class))).willReturn(Mono.just(comment));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient
                .mutateWith(csrf())
                .put().uri("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(comment))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.commentService, times(1)).update(comment);
    }

    @Test
    @DisplayName("Test create comment on post /api/comment")
    void shouldCreateComment() {
        Comment comment = new Comment("test", null, "test");
        Comment savedComment = comment();

        given(this.commentService.insert(any(Comment.class))).willReturn(Mono.just(savedComment));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient
                .mutateWith(csrf())
                .post().uri("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(comment))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class).isEqualTo(responseBody);

        verify(this.commentService, times(1)).insert(any(Comment.class));
    }

    @Test
    @DisplayName("Test delete comment on /api/comment/{id}")
    void shouldDeleteCommentById() {
        given(this.commentService.deleteById("123")).willReturn(Mono.empty());

        this.webClient
                .mutateWith(csrf())
                .delete().uri("/api/comment/123")
                .exchange()
                .expectStatus().isNoContent();

        verify(this.commentService, times(1)).deleteById("123");
    }

    private Comment comment() {
        return new Comment("test", dateTime(), "test");
    }

}