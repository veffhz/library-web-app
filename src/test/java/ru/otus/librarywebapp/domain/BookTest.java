package ru.otus.librarywebapp.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class BookTest {

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
        mongoTemplate.insert(book, "books");
        Book bookFromDb = mongoTemplate.findById(book.getId(), Book.class,"books");

        assertEquals(book, bookFromDb);
    }
}