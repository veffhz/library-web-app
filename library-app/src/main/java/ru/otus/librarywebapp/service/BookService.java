package ru.otus.librarywebapp.service;

import org.springframework.data.domain.Pageable;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    long count();
    Optional<Book> getById(String id);
    List<Book> getByBookName(String bookName);
    List<Book> getByBookPartName(String bookName);
    List<Book> getAll();
    BookDto getAll(Pageable pageable);
    List<Book> deleteByAuthorId(String authorId);
    List<Book> deleteByGenreId(String genreId);
    void deleteById(String id);
    Book insert(Book author);
    Book update(Book author);
}
