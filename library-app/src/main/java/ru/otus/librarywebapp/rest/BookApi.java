package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Book;

import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.service.BookService;

import javax.validation.Valid;

@Slf4j
@RestController
public class BookApi {

    private static final String BOOKS_SORT_FIELD = "bookName";

    private static final Sort BOOK_SORT = Sort.by(Sort.Direction.ASC, BOOKS_SORT_FIELD);

    private static final int BOOKS_PER_PAGE = 5;

    public static final PageRequest BOOK_PAGE_REQUEST = PageRequest.of(0, BOOKS_PER_PAGE, BOOK_SORT);

    private final BookService bookService;

    @Autowired
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public Mono<BookDto> getAll(@PageableDefault(size = BOOKS_PER_PAGE, sort = { BOOKS_SORT_FIELD },
            direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("get all books");
        return bookService.getAll(pageable);
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
