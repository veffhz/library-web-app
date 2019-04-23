package ru.otus.librarywebapp.exception;

public class CommentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Comment not found!";

    public CommentNotFoundException() {
        super(MESSAGE);
    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
