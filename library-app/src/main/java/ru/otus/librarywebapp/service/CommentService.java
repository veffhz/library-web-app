package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    long count();
    Optional<Comment> getById(String id);
    List<Comment> getByBookId(String bookId);
    List<Comment> getAll();
    CommentDto getAll(Pageable pageable);
    void deleteById(String id);
    List<Comment> deleteByBookId(String bookId);
    Comment update(Comment comment);
    Comment insert(Comment comment);
}
