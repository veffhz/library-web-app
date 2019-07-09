package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Genre;
import ru.otus.librarywebapp.exception.GenreNotFoundException;
import ru.otus.librarywebapp.service.GenreService;

import javax.validation.Valid;

@Slf4j
@RestController
public class GenreApi {

    private final GenreService genreService;

    @Autowired
    public GenreApi(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public Flux<Genre> getAll() {
        log.info("get all genres");
        return genreService.getAll();
    }

    @GetMapping("/api/genre/{id}")
    public Mono<Genre> getById(@PathVariable String id) {
        log.info("get genre by id {}", id);
        return genreService.getById(id).
                switchIfEmpty(Mono.error(GenreNotFoundException::new));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/genre")
    public Mono<Genre> update(@Valid @RequestBody Genre genre) {
        log.info("update genre {} by id {}", genre, genre.getId());
        return genreService.update(genre);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/genre")
    public Mono<Genre> create(@Valid @RequestBody Genre genre) {
        log.info("create genre {}", genre);
        return genreService.insert(genre);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/genre/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        log.info("delete genre by id {}", id);
        return genreService.deleteById(id);
    }

}
