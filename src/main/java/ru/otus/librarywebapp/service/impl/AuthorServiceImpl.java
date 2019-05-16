package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }

    @Override
    public Mono<Author> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Author> getByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public Flux<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        bookRepository.deleteByAuthorId(id);
        return repository.deleteById(id);
    }

    @Override
    public Mono<Author> insert(Author author) {
        return repository.insert(author);
    }

    @Override
    public Mono<Author> update(Author author) {
        return repository.save(author);
    }

}
