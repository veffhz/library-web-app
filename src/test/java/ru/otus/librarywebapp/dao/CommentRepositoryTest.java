package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import reactor.test.StepVerifier;

import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Comment;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test for CommentRepository")
@DataMongoTest
@ComponentScan({"ru.otus.librarywebapp.dao", "ru.otus.librarywebapp.testconfig"})
class CommentRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test add comment")
    void shouldAddCommentToBook() {
        Comment comment = new Comment("author", LocalDateTime.now(), "content");
        Book book = bookRepository.findAll().blockFirst();

        comment.setBook(book);

        StepVerifier
                .create(commentRepository.save(comment))
                .assertNext(c -> assertNotNull(c.getId()))
                .expectComplete()
                .verify();
    }

}