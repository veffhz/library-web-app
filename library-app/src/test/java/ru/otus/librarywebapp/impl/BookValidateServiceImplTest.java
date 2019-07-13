package ru.otus.librarywebapp.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;

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
import ru.otus.librarywebapp.service.impl.BookValidateServiceImpl;
import ru.otus.librarywebapp.utils.Helper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Test for Book validate service")
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "validate-service.url=http://mock.ru")
class BookValidateServiceImplTest {

    private static final String HTML_TEST_PAGE = "/test_1_sheckley.html";

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private BookValidateServiceImpl bookService;

    @Test
    void validate() throws IOException, URISyntaxException {
        URI uri = this.getClass().getResource(HTML_TEST_PAGE).toURI();
        String body = new String(Files.readAllBytes(Paths.get(uri)), "windows-1251");

        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

        AdditionalData additionalData = bookService.validate(book());

        assertEquals(additionalData.getItems().size(), 4);
    }

    private Book book() {
        Author author = new Author("Роберт", null, "Шекли");
        Genre genre = new Genre("Фантастика");
        return new Book(author, genre, "Избранное",
                Helper.toLocalDate("1991-01-01"), "russian","Мир", "Москва", "-");
    }

}