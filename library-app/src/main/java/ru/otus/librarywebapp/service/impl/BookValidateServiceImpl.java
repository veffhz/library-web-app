package ru.otus.librarywebapp.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Book;
import ru.otus.librarywebapp.service.BookValidateService;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookValidateServiceImpl implements BookValidateService {

    private static final Charset WIN_CHARSET = Charset.forName("cp1251");

    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public BookValidateServiceImpl(RestTemplate restTemplate, @Value("${validate-service.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public AdditionalData validate(Book book) {

        AdditionalData data = new AdditionalData();

        log.info("thread: {},  In: {}", Thread.currentThread().getName(), book.getBookName());

        try {
            UriComponentsBuilder param = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("title", book.getBookName())
                    .queryParam("authors", book.getAuthor().getLastName())
                    .queryParam("publisher", "")
                    .queryParam("isbn", "")
                    .queryParam("r", 0)
                    .queryParam("s", 1);

            HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

            ResponseEntity<String> responseEntity = restTemplate.exchange(param.build().encode(WIN_CHARSET).toUri(),
                    HttpMethod.GET, entity, String.class);

            if (log.isDebugEnabled()) {
                log.debug(responseEntity.getBody());
            }

            Document document = Jsoup.parse(Objects.requireNonNull(responseEntity.getBody()));

            Element meta = document.select("meta[http-equiv]").first();
            if (Objects.nonNull(meta) && "refresh".equals(meta.attr("http-equiv"))) {
                log.info("http-equiv=\"refresh\" find, try again...");
                responseEntity = restTemplate.exchange(param.build().encode(WIN_CHARSET).toUri(),
                        HttpMethod.GET, entity, String.class);

                document = Jsoup.parse(Objects.requireNonNull(responseEntity.getBody()));
            }

            Elements tds = document.select("td > big").stream()
                    .map(Element::parent)
                    .collect(Collectors.toCollection(Elements::new));

            for (Element td : tds) {
                Elements big = td.select("big");
                Elements small = td.select("small");

                data.getItems().add(big.text());
                data.getItems().add(small.text());
            }

            log.info("found {} items", data.getItems().size());

            if (data.isNotEmpty()) {
                data.setBook(book);
            } else {
                if (log.isTraceEnabled()) {  // TODO optional return
                    Path file = Files.createFile(Paths.get(Thread.currentThread().getName() + "-" + book.getBookName() + ".html"));
                    Files.write(file, responseEntity.getBody().getBytes());
                }
            }

        } catch (RestClientException e) {
            log.warn(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return data;
    }

}
