package ru.otus.validateapp.service;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookValidateService {
    void drop();
    AdditionalData validate(Book book);
    List<AdditionalData> findAll();
    void validate(List<Book> book);
    Optional<AdditionalData> findById(String id);
}
