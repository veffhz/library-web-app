package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.service.BookService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApi {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getAll() {
        log.info("get all books");
        return bookService.getAll();
    }

    @GetMapping("/api/books/{id}")
    public Book bookPage(@PathVariable String id) {
        log.info("get books by id {}",  id);
        return bookService.getById(id).orElseThrow(BookNotFoundException::new);
    }

    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@Valid @RequestBody Book book) {
        log.info("update book {} by id {}",  book, book.getId());
        Book updatedBook = bookService.update(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        log.info("create book {}",  book);
        Book savedBook = bookService.insert(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        log.info("delete book by id {}",  id);
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
