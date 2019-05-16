package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.librarywebapp.domain.Comment;


public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByBookId(String bookId);
    Flux<Comment> deleteByBookId(String bookId);
}
