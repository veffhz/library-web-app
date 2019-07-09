package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, PageableFindAll<Book> {
    Flux<Book> findByBookName(String bookName);
    Flux<Book> findByBookNameContaining(String bookName);
    Flux<Book> findByAuthorId(String authorId);
    Flux<Book> findByGenreId(String genreId);
    Flux<Book> deleteByAuthorId(String authorId); //TODO test
    Flux<Book> deleteByGenreId(String genreId);   //TODO test
}
