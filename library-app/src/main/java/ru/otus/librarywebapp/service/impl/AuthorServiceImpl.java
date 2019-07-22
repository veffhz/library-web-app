package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.otus.domain.Author;
import ru.otus.dto.AuthorDto;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;

import java.util.List;
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
    public AuthorDto getAll(Pageable pageable) {
        return new AuthorDto(repository.findAll(pageable).getContent());
    }

    @Override
    public void deleteById(String id) {
        bookService.deleteByAuthorId(id);
        repository.deleteById(id);
    }

    @Override
    public Author insert(Author author) {
        return repository.insert(author);
    }

    @Override
    public Author update(Author author) {
        return repository.save(author);
    }

}
