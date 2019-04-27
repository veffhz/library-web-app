package ru.otus.librarywebapp.utils;

import lombok.extern.java.Log;

import org.springframework.http.ResponseEntity;
import ru.otus.librarywebapp.exception.BookDateParseException;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log
public class Helper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static Date toDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BookDateParseException(e);
        }
    }

}
