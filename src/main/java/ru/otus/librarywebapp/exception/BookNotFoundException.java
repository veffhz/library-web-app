package ru.otus.librarywebapp.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Book not found!";

    public BookNotFoundException() {
        super(MESSAGE);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
