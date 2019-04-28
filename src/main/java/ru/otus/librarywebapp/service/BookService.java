package ru.otus.librarywebapp.service;

import ru.otus.librarywebapp.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    long count();
    Optional<Book> getById(String id);
    List<Book> getByBookName(String bookName);
    List<Book> getByBookPartName(String bookName);
    List<Book> getAll();
    List<Book> deleteByAuthorId(String authorId);
    void deleteById(String id);
    Book insert(Book author);
    Book update(Book author);
}
