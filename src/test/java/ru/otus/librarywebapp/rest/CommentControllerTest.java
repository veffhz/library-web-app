package ru.otus.librarywebapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;
import ru.otus.librarywebapp.utils.Helper;

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
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    //Все бины нужны, потому что иначе spring начинает искать mongoTemplate bean, и не находит
    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreService genreService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test get all comments on /api/comment")
    void shouldGetAllComments() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Comment comment = new Comment("test", date, "test");

        List<Comment> comments = Collections.singletonList(comment);
        given(this.commentService.getAll()).willReturn(comments);

        String responseBody = "[{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27\",\"content\":\"test\"}]";

        this.mvc.perform(get("/api/comment")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get comment on /api/comment{id}")
    void shouldGetComment() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Comment comment = new Comment("test", date, "test");

        given(this.commentService.getById("123")).willReturn(Optional.of(comment));

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27\",\"content\":\"test\"}";

        this.mvc.perform(get("/api/comment/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update comment page on /api/comment")
    void shouldUpdateComment() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Comment comment = new Comment("test", date, "test");

        given(this.commentService.update(comment)).willReturn(comment);

        ObjectMapper mapper = new ObjectMapper();

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27\",\"content\":\"test\"}";

        this.mvc.perform(put("/api/comment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.commentService, times(1)).update(any(Comment.class));
    }

    @Test
    @DisplayName("Test create comment on post /api/comment")
    void shouldCreateComment() throws Exception {
        Date date = Helper.toDate("2019-04-27");
        Comment comment = new Comment("test", null, "test");

        //given(this.commentRepository.insert(comment)).willReturn(comment);
        given(this.commentService.insert(comment)).willReturn(comment);

        ObjectMapper mapper = new ObjectMapper();

        String responseBody = "{\"id\":null,\"book\":null,\"author\":\"test\",\"date\":\"2019-04-27\",\"content\":\"test\"}";

        this.mvc.perform(post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
                //.andExpect(content().string(responseBody));
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

}