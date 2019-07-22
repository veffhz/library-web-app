package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.otus.domain.Author;

import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AuthorApi {

    private final AuthorService authorService;

    @Autowired
    public AuthorApi(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/author")
    public List<Author> getAll() {
        log.info("get all authors");
        return authorService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/author/{id}")
    public Author getById(@PathVariable String id) {
        log.info("get author by id {}", id);
        return authorService.getById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/author")
    public Author update(@Valid @RequestBody Author author) {
        log.info("update author {} by id {}", author, author.getId());
        return authorService.update(author);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/author")
    public Author create(@Valid @RequestBody Author author) {
        log.info("create author {}", author);
        return authorService.insert(author);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/author/{id}")
    public void delete(@PathVariable String id) {
        log.info("delete author by id {}", id);
        authorService.deleteById(id);
    }

}
