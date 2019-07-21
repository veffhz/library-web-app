package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Genre;

import ru.otus.dto.GenreDto;
import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.GenreService;
import ru.otus.librarywebapp.utils.Helper;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;
    private final BookService bookService;

    @Autowired
    public GenreServiceImpl(GenreRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }

    @Override
    public Mono<Genre> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Genre> getByGenreName(String genreName) {
        return repository.findByGenreName(genreName);
    }

    @Override
    public Flux<Genre> getAll() {
        return HystrixCommands
                .from(repository.findAll())
                .fallback(fallback())
                .commandName("findAll")
                .toFlux();
    }

    private Flux<Genre> fallback() {
        return Flux.fromIterable(Helper.notAvailableGenres());
    }

    @Override
    public Mono<GenreDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).collectList().map(GenreDto::new);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return bookService.deleteByGenreId(id).then(repository.deleteById(id));
    }

    @Override
    public Mono<Genre> insert(Genre genre) {
        return repository.insert(genre);
    }

    @Override
    public Mono<Genre> update(Genre genre) {
        return repository.save(genre);
    }

}
