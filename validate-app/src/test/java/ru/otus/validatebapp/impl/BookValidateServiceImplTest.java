package ru.otus.validatebapp.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import ru.otus.validateapp.dao.AdditionalDataRepository;
import ru.otus.validateapp.service.impl.BookValidateServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DisplayName("Test for book additional info service")
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "validate-service.url=http://mock.ru")
class BookValidateServiceImplTest {

    private static final String HTML_TEST_PAGE = "/test_1_sheckley.html";
    private static final String HTML_REDIRECT_TEST_PAGE = "/test_2_redirect.html";
    private static final String WINDOWS_1251 = "windows-1251";

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private AdditionalDataRepository additionalDataRepository;

    @SpyBean
    private BookValidateServiceImpl bookService;

    @Test
    @DisplayName("Test get additional data for book")
    void shouldGetAdditionalData() throws IOException, URISyntaxException {
        URI uri = this.getClass().getResource(HTML_TEST_PAGE).toURI();
        String body = new String(Files.readAllBytes(Paths.get(uri)), WINDOWS_1251);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.any(URI.class), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

        AdditionalData additionalData = bookService.validate(book());

        Assertions.assertEquals(additionalData.getItems().size(), 4);
    }

    @Test
    @DisplayName("Test get additional data for book with redirect")
    void shouldGetAdditionalDataWithRedirect() throws IOException, URISyntaxException {
        URI uri = this.getClass().getResource(HTML_REDIRECT_TEST_PAGE).toURI();
        String body = new String(Files.readAllBytes(Paths.get(uri)), WINDOWS_1251);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.any(URI.class), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

        bookService.validate(book());

        Mockito.verify(restTemplate, Mockito.times(2)).exchange(ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(), ArgumentMatchers.<Class<String>>any());
    }

    private Book book() {
        Author author = new Author("Роберт", null, "Шекли");
        Genre genre = new Genre("Фантастика");
        return new Book(author, genre, "Избранное",
                toLocalDate("1991-01-01"), "russian","Мир", "Москва", "-");
    }

    private static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}