package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Comment;

import ru.otus.dto.CommentDto;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.rest.CommentApi;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.utils.Helper;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
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
        return HystrixCommands
                .from(repository.findAll())
                .fallback(fallback())
                .commandName("findAll")
                .toFlux();
    }

    @Override
    public Mono<CommentDto> getAll(Pageable pageable) {
        return HystrixCommands.from(repository.findAll(pageable).collectList().zipWith(repository.count())
                .map(data -> new CommentDto(data.getT1(), pageable.getPageNumber(),
                        data.getT2() / CommentApi.COMMENTS_PER_PAGE)))
                .fallback(fallbackMono())
                .commandName("findAll")
                .toMono();
    }

    private Flux<Comment> fallback() {
        return Flux.fromIterable(Helper.notAvailableComments());
    }

    private Mono<CommentDto> fallbackMono() {
        return Mono.just(Helper.notAvailableCommentDto());
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
