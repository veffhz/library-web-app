package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.GenreService;

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
    public Mono<Long> count() {
        return repository.count();
    }

    public Mono<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Book> getByBookName(String bookName) {
        return repository.findByBookName(bookName);
    }

    @Override
    public Flux<Book> getByBookPartName(String bookName) {
        return repository.findByBookNameContaining(bookName);
    }

    @Override
    public Flux<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Book> deleteByAuthorId(String authorId) {
        return repository.deleteByAuthorId(authorId);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        commentRepository.deleteByBookId(id);
        return repository.deleteById(id);
    }

    @Override
    public Mono<Book> insert(Book book) {
        return repository.insert(book);
    }

    @Override
    public Mono<Book> update(Book book) {
        return repository.save(book);
    }

}
