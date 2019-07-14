package ru.otus.librarywebapp.service;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Book;

public interface BookValidateService {
    AdditionalData validate(Book book);
}
