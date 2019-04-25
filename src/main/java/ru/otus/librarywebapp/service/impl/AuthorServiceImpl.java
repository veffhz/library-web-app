package ru.otus.librarywebapp.service.impl;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.util.Strings;

import org.springframework.stereotype.Service;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.service.AuthorService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

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
        bookRepository.deleteByAuthorId(id);
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
        Author authorDb;
        if (Strings.isNotEmpty(author.getId())) {
            authorDb = repository.save(author);
        } else {
            authorDb = repository.insert(author);
        }
        return Objects.nonNull(authorDb) ? authorDb.getId() : null;
    }

}
