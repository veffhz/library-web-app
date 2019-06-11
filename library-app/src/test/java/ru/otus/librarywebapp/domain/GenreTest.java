package ru.otus.librarywebapp.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.otus.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class GenreTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void saveAndGet() {
        Genre genre = new Genre("Genre");
        mongoTemplate.insert(genre, "genres");

        Genre genreFromDb = mongoTemplate.findById(genre.getId(), Genre.class,"genres");

        assertEquals(genre, genreFromDb);
    }
}