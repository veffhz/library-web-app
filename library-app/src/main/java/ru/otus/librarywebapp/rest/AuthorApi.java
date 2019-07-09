package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Author;

import ru.otus.dto.AuthorDto;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthorApi {

    private static final String AUTHORS_SORT_FIELD = "lastName";

    private static final Sort AUTHOR_SORT = Sort.by(Sort.Direction.ASC, AUTHORS_SORT_FIELD);

    private static final int AUTHORS_PER_PAGE = 5;

    public static final PageRequest AUTHORS_PAGE_REQUEST = PageRequest.of(0, AUTHORS_PER_PAGE, AUTHOR_SORT);

    private final AuthorService authorService;

    @Autowired
    public AuthorApi(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/author")
    public Mono<AuthorDto> getAll(@PageableDefault(size = AUTHORS_PER_PAGE, sort = { AUTHORS_SORT_FIELD },
            direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("get all authors");
        return authorService.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/author/{id}")
    public Mono<Author> getById(@PathVariable String id) {
        log.info("get author by id {}", id);
        return authorService.getById(id)
                .switchIfEmpty(Mono.error(AuthorNotFoundException::new));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/author")
    public Mono<Author> update(@Valid @RequestBody Author author) {
        log.info("update author {} by id {}", author, author.getId());
        return authorService.update(author);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/author")
    public Mono<Author> create(@Valid @RequestBody Author author) {
        log.info("create author {}", author);
        return authorService.insert(author);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/author/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        log.info("delete author by id {}", id);
        return authorService.deleteById(id);
    }

}
