package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;
import ru.otus.librarywebapp.service.CommentService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for comment api")
@WebMvcTest(controllers = CommentApi.class)
@Import(CommentApi.class)
class CommentControllerTest extends BaseTest {

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test get all comments on /api/comment")
    void shouldGetAllComments() throws Exception {
        given(this.commentService.getAll(CommentApi.COMMENTS_PAGE_REQUEST))
                .willReturn(new CommentDto(Collections.singletonList(comment()), 0, 1L));

        String responseBody = "{\"comments\":[{\"id\":null,\"book\":null,\"author\":\"test\"," +
                "\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}],\"currentPage\":0,\"totalPages\":1}";

        this.webClient.perform(get("/api/comment").param("page", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get comment on /api/comment{id}")
    void shouldGetComment() throws Exception {
        given(this.commentService.getById("123")).willReturn(Optional.of(comment()));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient.perform(get("/api/comment/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update comment page on /api/comment")
    void shouldUpdateComment() throws Exception {
        Comment comment = comment();

        given(this.commentService.update(any(Comment.class))).willReturn(comment);

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient.perform(put("/api/comment").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.commentService, times(1)).update(comment);
    }

    @Test
    @DisplayName("Test create comment on post /api/comment")
    void shouldCreateComment() throws Exception {
        Comment comment = new Comment("test", null, "test");
        Comment savedComment = comment();

        given(this.commentService.insert(any(Comment.class))).willReturn(savedComment);

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.webClient.perform(post("/api/comment").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));

        verify(this.commentService, times(1)).insert(any(Comment.class));
    }

    @Test
    @DisplayName("Test delete comment on /api/comment/{id}")
    void shouldDeleteCommentById() throws Exception {
        this.webClient.perform(delete("/api/comment/123").with(csrf()))
                .andExpect(status().isNoContent());

        verify(this.commentService, times(1)).deleteById("123");
    }

    private Comment comment() {
        return new Comment("test", dateTime(), "test");
    }

}