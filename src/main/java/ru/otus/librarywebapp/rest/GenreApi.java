package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.exception.GenreNotFoundException;
import ru.otus.librarywebapp.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GenreApi {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<Genre> getAll() {
        log.info("get all genres");
        return genreService.getAll();
    }

    @GetMapping("/api/genre/{id}")
    public Genre getById(@PathVariable String id) {
        log.info("get genre by id {}", id);
        return genreService.getById(id).orElseThrow(GenreNotFoundException::new);
    }

    @PutMapping("/api/genre")
    public ResponseEntity<Genre> update(@Valid @RequestBody Genre genre) {
        log.info("update genre {} by id {}", genre, genre.getId());
        Genre updatedGenre = genreService.update(genre);
        return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
    }

    @PostMapping("/api/genre")
    public ResponseEntity<Genre> create(@Valid @RequestBody Genre genre) {
        log.info("create genre {}", genre);
        Genre savedGenre = genreService.insert(genre);
        return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/genre/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        log.info("delete genre by id {}", id);
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
