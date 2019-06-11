package ru.otus.librarywebapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.exception.CommentNotFoundException;
import ru.otus.librarywebapp.exception.GenreNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {AuthorNotFoundException.class, BookNotFoundException.class,
            CommentNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<String> handle(RuntimeException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
