package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, PageableFindAll<Comment> {
    List<Comment> findByBookId(String bookId);
    List<Comment> deleteByBookId(String bookId);
}
