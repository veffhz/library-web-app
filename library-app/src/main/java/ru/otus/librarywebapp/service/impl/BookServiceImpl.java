package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.otus.dto.BookDto;
import ru.otus.domain.Book;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.rest.BookApi;
import ru.otus.librarywebapp.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
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
    public BookDto getAll(Pageable pageable) {
        Page<Book> page = repository.findAll(pageable);
        return new BookDto(page.getContent(), pageable.getPageNumber(),
                page.getTotalElements() / BookApi.BOOKS_PER_PAGE);
    }

    @Override
    public List<Book> deleteByAuthorId(String authorId) {
        List<Book> byAuthorId = repository.findByAuthorId(authorId);
        byAuthorId.forEach(book -> commentRepository.deleteByBookId(book.getId()));
        return repository.deleteByAuthorId(authorId);
    }

    @Override
    public List<Book> deleteByGenreId(String genreId) {
        List<Book> byGenreId = repository.findByGenreId(genreId);
        byGenreId.forEach(book -> commentRepository.deleteByBookId(book.getId()));
        return repository.deleteByGenreId(genreId);
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
