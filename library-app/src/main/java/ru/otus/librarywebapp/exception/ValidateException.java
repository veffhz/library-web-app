package ru.otus.librarywebapp.exception;

public class ValidateException extends RuntimeException {

    private static final String MESSAGE = "validate error!";

    public ValidateException() {
        super(MESSAGE);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
