package ru.otus.librarywebapp.exception;

public class GenreNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Genre not found!";

    public GenreNotFoundException() {
        super(MESSAGE);
    }

    public GenreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
