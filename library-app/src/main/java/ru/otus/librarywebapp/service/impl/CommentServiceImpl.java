package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.rest.CommentApi;
import ru.otus.librarywebapp.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
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
    public CommentDto getAll(Pageable pageable) {
        Page<Comment> page = repository.findAll(pageable);
        return new CommentDto(page.getContent(), pageable.getPageNumber(),
                page.getTotalElements() / CommentApi.COMMENTS_PER_PAGE);
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
