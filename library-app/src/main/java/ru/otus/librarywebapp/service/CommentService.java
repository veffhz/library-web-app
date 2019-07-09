package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

public interface CommentService {
    Mono<Long> count();
    Mono<Comment> getById(String id);
    Flux<Comment> getByBookId(String bookId);
    Flux<Comment> getAll();
    Mono<CommentDto> getAll(Pageable pageable);
    Mono<Void> deleteById(String id);
    Flux<Comment> deleteByBookId(String bookId);
    Mono<Comment> update(Comment comment);
    Mono<Comment> insert(Comment comment);
}
