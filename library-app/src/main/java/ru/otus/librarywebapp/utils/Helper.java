package ru.otus.librarywebapp.utils;

import lombok.extern.java.Log;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;

import ru.otus.librarywebapp.exception.BookDateParseException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Date;

@Log
public class Helper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_FORMAT);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static Date toDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BookDateParseException(e);
        }
    }

    public static Date toDateTime(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BookDateParseException(e);
        }
    }

    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDate.parse(date, formatter).atStartOfDay();
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public static Iterable<Author> notAvailableAuthors() {
        return Arrays.asList(author(), author());
    }

    public static Iterable<Genre> notAvailableGenres() {
        return Arrays.asList(genre(), genre());
    }

    public static Iterable<Book> notAvailableBooks() {
        return Arrays.asList(book(), book());
    }

    public static BookDto notAvailableBookDto() {
        return new BookDto(Arrays.asList(book(), book()), 0 ,0L);
    }

    public static CommentDto notAvailableCommentDto() {
        return new CommentDto(Arrays.asList(comment(), comment()), 0 ,0L);
    }

    public static Iterable<Comment> notAvailableComments() {
        return Arrays.asList(comment(), comment());
    }

    public static Comment comment() {
        return new Comment("Other comments will",
                LocalDateTime.now(), "be available later");
    }

    public static Author author() {
        return new Author("Other authors will",
                LocalDate.now(), "be available later");
    }

    public static Genre genre() {
        return new Genre("Other genres will be available later");
    }


    public static Book book() {
        return new Book(author(), genre(), "Other books will be available later",
                LocalDate.now(), "En", "", "", "555-555");
    }

}
