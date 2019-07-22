package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    public static final int BOOKS_PER_PAGE = 15;

    public static final PageRequest BOOK_PAGE_REQUEST = PageRequest.of(0, BOOKS_PER_PAGE, BOOK_SORT);

    private final BookService bookService;

    @Autowired
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public BookDto getAll(@RequestParam("page") int page) {
        log.info("get all books, page {}", page);
        return bookService.getAll(PageRequest.of(page, BOOKS_PER_PAGE, BOOK_SORT));
    }

    @GetMapping("/api/book/{id}")
    public Book getById(@PathVariable String id) {
        log.info("get books by id {}", id);
        return bookService.getById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/book")
    public Book update(@Valid @RequestBody Book book) {
        log.info("update book {} by id {}", book, book.getId());
        return bookService.update(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/book")
    public Book create(@Valid @RequestBody Book book) {
        log.info("create book {}", book);
        return bookService.insert(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/book/{id}")
    public void delete(@PathVariable String id) {
        log.info("delete book by id {}", id);
        bookService.deleteById(id);
    }

}
