package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

public interface BookService {
    Mono<Long> count();
    Mono<Book> getById(String id);
    Flux<Book> getByBookName(String bookName);
    Flux<Book> getByBookPartName(String bookName);
    Flux<Book> getAll();
    Mono<BookDto> getAll(Pageable pageable);
    Flux<Book> deleteByAuthorId(String authorId);
    Flux<Book> deleteByGenreId(String genreId);
    Mono<Void> deleteById(String id);
    Mono<Book> insert(Book author);
    Mono<Book> update(Book author);
}
