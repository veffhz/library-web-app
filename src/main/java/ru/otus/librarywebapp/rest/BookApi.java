package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.service.BookService;

import javax.validation.Valid;

@Slf4j
@RestController
public class BookApi {

    private final BookService bookService;

    @Autowired
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public Flux<Book> getAll() {
        log.info("get all books");
        return bookService.getAll();
    }

    @GetMapping("/api/book/{id}")
    public Mono<Book> getById(@PathVariable String id) {
        log.info("get books by id {}", id);
        return bookService.getById(id)
                .switchIfEmpty(Mono.error(BookNotFoundException::new));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/book")
    public Mono<Book> update(@Valid @RequestBody Book book) {
        log.info("update book {} by id {}", book, book.getId());
        return bookService.update(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/book")
    public Mono<Book> create(@Valid @RequestBody Book book) {
        log.info("create book {}", book);
        return bookService.insert(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/book/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        log.info("delete book by id {}", id);
        return bookService.deleteById(id);
    }

}
