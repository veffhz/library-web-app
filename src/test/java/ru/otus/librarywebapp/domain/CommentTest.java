package ru.otus.librarywebapp.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CommentTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void saveAndGet() {
        Author author = new Author("FirstName", LocalDate.now(), "LastName");
        mongoTemplate.insert(author, "authors");

        Genre genre = new Genre("Genre");
        mongoTemplate.insert(genre, "genres");

        Book book = new Book(author, genre, "Best", LocalDate.now(), "russian",
                "Test", "Test", "555-555");
        mongoTemplate.insert(book);

        Comment comment = new Comment( "Best", LocalDateTime.now(), "russian");
        comment.setBook(book);
        mongoTemplate.insert(comment);

        Comment commentFromDb = mongoTemplate.findById(comment.getId(), Comment.class,"comments");
        assertEquals(comment, commentFromDb);
    }

}