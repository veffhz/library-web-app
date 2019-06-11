package ru.otus.librarywebapp.exception;

public class AuthorNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Author not found!";

    public AuthorNotFoundException() {
        super(MESSAGE);
    }

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
