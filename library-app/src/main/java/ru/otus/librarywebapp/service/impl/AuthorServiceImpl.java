package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Author;

import ru.otus.dto.AuthorDto;
import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
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
    public Mono<AuthorDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).collectList().zipWith(repository.count())
                .map(data -> new AuthorDto(data.getT1(), pageable.getPageNumber(), data.getT2()));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return bookService.deleteByAuthorId(id).then(repository.deleteById(id));
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
