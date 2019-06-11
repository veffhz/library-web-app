package ru.otus.librarywebapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ru.otus.librarywebapp.utils.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@WithMockUser(username = "usr", authorities = "ROLE_USER")
class BaseTest {

    private static final String DATE = "2019-04-27";

    @Autowired
    WebTestClient webClient;

    @Autowired
    private ObjectMapper mapper;

    LocalDate date() {
        return mapper.convertValue(DATE, LocalDate.class);
    }

    LocalDateTime dateTime() {
        return Helper.toLocalDateTime(DATE);
    }

}
