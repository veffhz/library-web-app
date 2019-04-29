package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.otus.librarywebapp.utils.Helper.toDate;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final AuthorRepository authorRepository;
    private final GenreService genreService;
    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository,
                           GenreService genreService, CommentRepository commentRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreService = genreService;
        this.commentRepository = commentRepository;
    }

    @Override
    public long count() {
        return repository.count();
    }

    public Optional<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Book> getByBookName(String bookName) {
        return repository.findByBookName(bookName);
    }

    @Override
    public List<Book> getByBookPartName(String bookName) {
        return repository.findByBookNameContaining(bookName);
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> deleteByAuthorId(String authorId) {
        return repository.deleteByAuthorId(authorId);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteByBookId(id);
        repository.deleteById(id);
    }

    @Override
    public Book insert(Book book) {
        return repository.insert(book);
    }

    @Override
    public Book update(Book book) {
        return repository.save(book);
    }

}
