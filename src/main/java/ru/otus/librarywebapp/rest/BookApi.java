package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookApi {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/api/books/{id}")
    public Book bookPage(@PathVariable String id) {
        return bookService.getById(id).orElseThrow(BookNotFoundException::new);
    }

    @PutMapping("/api/books")
    public ResponseEntity update(@Valid @RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/books")
    public ResponseEntity create(@Valid @RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
