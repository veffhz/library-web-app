package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                .switchIfEmpty(Mono.error((BookNotFoundException::new)));
    }

    @PutMapping("/api/book")
    public Mono<ResponseEntity<Book>> update(@Valid @RequestBody Book book) {
        log.info("update book {} by id {}", book, book.getId());
        return bookService.update(book)
                .map(updatedBook -> new ResponseEntity<>(updatedBook, HttpStatus.OK));
    }

    @PostMapping("/api/book")
    public Mono<ResponseEntity<Book>> create(@Valid @RequestBody Book book) {
        log.info("create book {}", book);
        return bookService.insert(book)
                .map(savedBook -> new ResponseEntity<>(savedBook, HttpStatus.CREATED));
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("delete book by id {}", id);
        return bookService.deleteById(id)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

}
