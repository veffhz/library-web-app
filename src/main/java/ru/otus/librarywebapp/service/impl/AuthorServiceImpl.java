package ru.otus.librarywebapp.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Author> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Author> getByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        bookService.deleteByAuthorId(id);
        repository.deleteById(id);
    }

    @Override
    public String insert(String firstName, Date birthDate, String lastName) {
        Author author = new Author(firstName, birthDate, lastName);
        Author authorDb = repository.save(author);
        return Objects.nonNull(authorDb) ? authorDb.getId() : null;
    }

    @Override
    public String save(Author author) {
        Author authorDb = repository.save(author);
        return Objects.nonNull(authorDb) ? authorDb.getId() : null;
    }

}
