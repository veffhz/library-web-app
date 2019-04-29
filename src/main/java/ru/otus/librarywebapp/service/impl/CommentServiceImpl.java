package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.CommentService;

import java.util.List;
import java.util.Optional;

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
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Comment> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Comment> getByBookId(String bookId) {
        return repository.findByBookId(bookId);
    }

    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Comment> deleteByBookId(String bookId) {
        return repository.deleteByBookId(bookId);
    }

    @Override
    public Comment insert(Comment comment) {
        return repository.insert(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return repository.save(comment);
    }
}
