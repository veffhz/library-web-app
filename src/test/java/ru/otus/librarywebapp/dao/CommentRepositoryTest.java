package ru.otus.librarywebapp.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Comment comment = new Comment("author", new Date(), "content");
        Book book = bookRepository.findAll().get(0);

        comment.setBook(book);
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findAll();

        assertEquals(comments.size(), 1);
    }
}