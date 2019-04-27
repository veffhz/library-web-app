package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<Author> getAll() {
        log.info("get all authors");
        return authorService.getAll();
    }

    @GetMapping("/api/author/{id}")
    public Author getById(@PathVariable String id) {
        log.info("get author by id {}", id);
        return authorService.getById(id).orElseThrow(AuthorNotFoundException::new);
    }

    @PutMapping("/api/author")
    public ResponseEntity<Author> update(@Valid @RequestBody Author author) {
        log.info("update author {} by id {}", author, author.getId());
        Author updatedAuthor = authorService.update(author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @PostMapping("/api/author")
    public ResponseEntity<Author> create(@Valid @RequestBody Author author) {
        log.info("create author {}", author);
        Author savedAuthor = authorService.insert(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/author/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        log.info("delete author by id {}", id);
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
