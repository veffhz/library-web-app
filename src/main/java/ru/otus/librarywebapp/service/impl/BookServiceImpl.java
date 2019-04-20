package ru.otus.librarywebapp.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.otus.librarywebapp.utils.Helper.toDate;

@Log
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Autowired
    public BookServiceImpl(BookRepository repository, @Lazy AuthorService authorService,
                           GenreService genreService, @Lazy CommentService commentService) {
        this.repository = repository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
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
        commentService.deleteByBookId(id);
        repository.deleteById(id);
    }

    @Override
    public String insert(String authorId, String genreId, String bookName, String publishDate, String language,
                      String publishingHouse, String city, String isbn) {
        Optional<Author> author = authorService.getById(authorId);
        Optional<Genre> genre = genreService.getById(genreId);
        Book book = new Book(author.orElse(new Author()), genre.orElse(new Genre()), bookName, toDate(publishDate), language, publishingHouse, city, isbn);
        Book bookDb = repository.save(book);
        return Objects.nonNull(bookDb) ? bookDb.getId() : null;
    }

}
