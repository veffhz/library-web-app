package ru.otus.validateapp.service;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Book;

import java.util.List;

public interface BookValidateService {
    AdditionalData validate(Book book);
    void validate(List<Book> book);
    AdditionalData findById(String id);
}
