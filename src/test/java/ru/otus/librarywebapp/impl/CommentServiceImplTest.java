package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.impl.CommentServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test for Comment Service")
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    @SpyBean
    private CommentServiceImpl commentService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test invoke get comment by id")
    void shouldGetGenreById() {
        Mono<Comment> commentMock = Mono.just(new Comment("test", LocalDateTime.now(), "test"));
        when(commentRepository.findById(any(String.class))).thenReturn(commentMock);

        Mono<Comment> comment = commentService.getById("000");

        verify(commentRepository, times(1)).findById("000");
        assertEquals(commentMock, comment);
    }

    @Test
    @DisplayName("Test invoke get all comments")
    void shouldGetAllGenres() {
        commentService.getAll();
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test invoke delete comment by id")
    void shouldDeleteGenreById() {
        commentService.deleteById("000");
        verify(commentRepository, times(1)).deleteById("000");
    }

    @Test
    @DisplayName("Test invoke insert new comment")
    void shouldInsertNewGenre() {
        Comment comment = new Comment("test", LocalDateTime.now(), "test");
        commentService.insert(comment);
        verify(commentRepository, times(1)).insert(comment);
    }
}