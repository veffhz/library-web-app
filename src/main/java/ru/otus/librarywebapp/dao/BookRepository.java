package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.librarywebapp.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByBookName(String bookName);
    List<Book> findByBookNameContaining(String bookName);
    List<Book> deleteByAuthorId(String authorId);
}
