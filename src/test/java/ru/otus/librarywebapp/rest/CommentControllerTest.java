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

import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.utils.Helper;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for comment Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CommentApi.class)
@Import(CommentApi.class)
class CommentControllerTest {

    private static final String DATE = "2019-04-27";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test get all comments on /api/comment")
    void shouldGetAllComments() throws Exception {
        Comment comment = new Comment("test", date(), "test");
        List<Comment> comments = Collections.singletonList(comment);

        given(this.commentService.getAll()).willReturn(comments);

        String responseBody = "[{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}]";

        this.mvc.perform(get("/api/comment")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get comment on /api/comment{id}")
    void shouldGetComment() throws Exception {
        Comment comment = new Comment("test", date(), "test");

        given(this.commentService.getById("123")).willReturn(Optional.of(comment));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.mvc.perform(get("/api/comment/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update comment page on /api/comment")
    void shouldUpdateComment() throws Exception {
        Comment comment = new Comment("test", date(), "test");

        given(this.commentService.update(any(Comment.class))).willReturn(comment);

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.mvc.perform(put("/api/comment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.commentService, times(1)).update(comment);
    }

    @Test
    @DisplayName("Test create comment on post /api/comment")
    void shouldCreateComment() throws Exception {
        Comment comment = new Comment("test", null, "test");
        Comment savedComment = new Comment("test", date(), "test");

        given(this.commentService.insert(any(Comment.class))).willReturn(savedComment);

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27 00:00\",\"content\":\"test\"}";

        this.mvc.perform(post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));
        verify(this.commentService, times(1)).insert(any(Comment.class));
    }

    @Test
    @DisplayName("Test delete comment on /api/comment/{id}")
    void shouldDeleteCommentById() throws Exception {
        this.mvc.perform(delete("/api/comment/123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());
        verify(this.commentService, times(1)).deleteById("123");
    }

    private LocalDateTime date(){
        return Helper.toLocalDateTime(DATE);
    }

}