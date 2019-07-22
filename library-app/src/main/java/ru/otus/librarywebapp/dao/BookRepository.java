package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, PageableFindAll<Book> {
    List<Book> findByBookName(String bookName);
    List<Book> findByBookNameContaining(String bookName);
    List<Book> findByAuthorId(String authorId);
    List<Book> findByGenreId(String genreId);
    List<Book> deleteByAuthorId(String authorId); //TODO test
    List<Book> deleteByGenreId(String genreId);   //TODO test
}
