package ru.otus.validateapp.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
import ru.otus.validateapp.dao.AdditionalDataRepository;
import ru.otus.validateapp.exception.ValidateException;
import ru.otus.validateapp.service.BookValidateService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookValidateServiceImpl implements BookValidateService {

    private static final Charset WIN_CHARSET = Charset.forName("cp1251");

    private final AdditionalDataRepository repository;

    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public BookValidateServiceImpl(AdditionalDataRepository repository, RestTemplate restTemplate,
                                   @Value("${validate-service.url}") String url) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultAdditionalData", groupKey = "BookValidateService", commandKey = "validate",
            commandProperties = {@HystrixProperty(name="execution.timeout.enabled", value="false")})
    public List<AdditionalData> validate(List<Book> books) {
        List<AdditionalData> items = books.stream().map(this::validate)
                .filter(AdditionalData::isNotEmpty)
                .collect(Collectors.toList());
        items.forEach(item -> {
            log.info("save {}", item);
            repository.save(item);
        });
        return items;
    }

    @Override
    public Optional<AdditionalData> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<AdditionalData> findAll() {
        return repository.findAll();
    }

    @Override
    public void drop() {
        repository.deleteAll();
    }

    public AdditionalData validate(Book book) {

        AdditionalData data = new AdditionalData();

        log.info("In: {}", book.getBookName());

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
                    saveRawData(book.getBookName(), responseEntity.getBody().getBytes());
                }
            }

        } catch (RestClientException e) {
            log.warn(e.getMessage(), e);
            throw new ValidateException(e.getMessage(), e);
        }

        return data;
    }

    private List<AdditionalData> defaultAdditionalData(List<Book> books) {
        log.warn("fallback!");
        return Collections.singletonList(new AdditionalData("1a1a111aa1a1aa111a1a1111",
                books.iterator().next()));
    }

    private void saveRawData(String bookName, byte[] bytes) {
        try {
            Path file = Files.createFile(Paths.get(Thread.currentThread().getName() + "-" + bookName + ".html"));
            Files.write(file, bytes);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
