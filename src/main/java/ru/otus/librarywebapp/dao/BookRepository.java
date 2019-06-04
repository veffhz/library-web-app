package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.librarywebapp.domain.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByBookName(String bookName);
    Flux<Book> findByBookNameContaining(String bookName);
    Flux<Book> findByAuthorId(String authorId);
    Flux<Book> deleteByAuthorId(String authorId);
}
