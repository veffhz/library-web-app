package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import ru.otus.domain.Genre;
import ru.otus.dto.GenreDto;
import ru.otus.librarywebapp.exception.GenreNotFoundException;
import ru.otus.librarywebapp.service.GenreService;

import javax.validation.Valid;

@Slf4j
@RestController
public class GenreApi {

    private static final String GENRES_SORT_FIELD = "genreName";

    private static final Sort GENRE_SORT = Sort.by(Sort.Direction.ASC, GENRES_SORT_FIELD);

    private static final int GENRES_PER_PAGE = 5;

    public static final PageRequest GENRE_PAGE_REQUEST = PageRequest.of(0, GENRES_PER_PAGE, GENRE_SORT);

    private final GenreService genreService;

    @Autowired
    public GenreApi(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public Mono<GenreDto> getAll(@PageableDefault(size = GENRES_PER_PAGE, sort = { GENRES_SORT_FIELD },
            direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("get all genres");
        return genreService.getAll(pageable);
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
