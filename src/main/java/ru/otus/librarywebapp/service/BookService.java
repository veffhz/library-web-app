package ru.otus.librarywebapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Book;

public interface BookService {
    Mono<Long> count();
    Mono<Book> getById(String id);
    Flux<Book> getByBookName(String bookName);
    Flux<Book> getByBookPartName(String bookName);
    Flux<Book> getAll();
    Flux<Book> deleteByAuthorId(String authorId);
    Mono<Void> deleteById(String id);
    Mono<Book> insert(Book author);
    Mono<Book> update(Book author);
}
