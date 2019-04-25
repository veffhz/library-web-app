package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/api/authors/{id}")
    public Author getById(@PathVariable String id) {
        return authorService.getById(id).orElseThrow(AuthorNotFoundException::new);
    }

    @PutMapping("/api/authors")
    public ResponseEntity update(@Valid @RequestBody Author author) {
        authorService.save(author);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/authors")
    public ResponseEntity create(@Valid @RequestBody Author author) {
        String id = authorService.save(author);
        return ResponseEntity.created(URI.create(String.format("/api/authors/%s", id))).build();
    }

    @DeleteMapping("/api/authors/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
