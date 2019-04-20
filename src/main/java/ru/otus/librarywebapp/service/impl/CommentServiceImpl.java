package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.otus.librarywebapp.utils.Helper.toDate;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
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
    public String insert(String author, String date, String content, String bookId) {
        Comment comment = new Comment(author, toDate(date), content);
        Book book = bookService.getById(bookId).get(); // TODO get
        comment.setBook(book);
        Comment commentDb = repository.save(comment);
        return Objects.nonNull(commentDb) ? commentDb.getId() : null;
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
}
