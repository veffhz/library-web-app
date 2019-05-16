package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }

    @Override
    public Mono<Comment> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Comment> getByBookId(String bookId) {
        return repository.findByBookId(bookId);
    }

    @Override
    public Flux<Comment> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    @Transactional
    public Flux<Comment> deleteByBookId(String bookId) {
        return repository.deleteByBookId(bookId);
    }

    @Override
    public Mono<Comment> insert(Comment comment) {
        return repository.insert(comment);
    }

    @Override
    public Mono<Comment> update(Comment comment) {
        return repository.save(comment);
    }
}
