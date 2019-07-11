package ru.otus.librarywebapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ru.otus.domain.Book;
import ru.otus.librarywebapp.service.BookValidateService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BookValidateServiceImpl implements BookValidateService {

    private final RestTemplate client;
    private final String urlValidateService;

    @Autowired
    public BookValidateServiceImpl(@Value("${validate-service.url}") String urlValidateService,
                                   RestTemplateBuilder builder) {
        this.client = builder.build();
        this.urlValidateService = urlValidateService;
    }

    @Override
    public Book validate(Book book) {
        log.info("{}", Thread.currentThread().getName());

        if (!book.getAuthor().isAvailable()) {
            log.info("skip!");
            return new Book();
        }

        log.info("In: {}", book.getBookName());

        try {
            //title=Paskal&authors=Kotov&isbn=isbn&publisher=house&r=0&s=1&viewsize=15&startidx=0
            //title=Paskal&authors=Kotov&r=0&s=1&viewsize=15&startidx=0
            Map<String, String> vars = new HashMap<>();

            vars.put("title", book.getBookName());
            vars.put("authors", book.getAuthor().getLastName());
            vars.put("startidx", "0");
            vars.put("r", "0");
            vars.put("s", "1");

            //String response = client.getForObject(urlValidateService, String.class, vars);
            //log.info(response);

            Thread.sleep(6000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Out: {}", book.getBookName());
        return new Book(null, null, "###", book.getPublishDate(),
                book.getLanguage(), book.getPublishingHouse(), book.getCity(), book.getIsbn());
    }

}
