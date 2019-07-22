package ru.otus.librarywebapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface PageableFindAll<T> {
    @Query("{ id: { $exists: true }}")
    Page<T> findAll(Pageable pageable);
}
