package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Genre> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Genre> getByGenreName(String genreName) {
        return repository.findByGenreName(genreName);
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Genre insert(Genre genre) {
        return repository.insert(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return repository.save(genre);
    }

}
