package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
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
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
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
