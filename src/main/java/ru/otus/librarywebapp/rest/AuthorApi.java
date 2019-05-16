package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthorApi {

    private final AuthorService authorService;

    @Autowired
    public AuthorApi(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    public Flux<Author> getAll() {
        log.info("get all authors");
        return authorService.getAll();
    }

    @GetMapping("/api/author/{id}")
    public Mono<Author> getById(@PathVariable String id) {
        log.info("get author by id {}", id);
        return authorService.getById(id)
                .switchIfEmpty(Mono.error((AuthorNotFoundException::new)));
    }

    @PutMapping("/api/author")
    public Mono<ResponseEntity<Author>> update(@Valid @RequestBody Author author) {
        log.info("update author {} by id {}", author, author.getId());
        return authorService.update(author)
                .map(updatedAuthor -> new ResponseEntity<>(updatedAuthor, HttpStatus.OK));
    }

    @PostMapping("/api/author")
    public Mono<ResponseEntity<Author>> create(@Valid @RequestBody Author author) {
        log.info("create author {}", author);
        return authorService.insert(author).
                map(savedAuthor -> new ResponseEntity<>(savedAuthor, HttpStatus.CREATED));
    }

    @DeleteMapping("/api/author/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("delete author by id {}", id);
        return authorService.deleteById(id)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

}
