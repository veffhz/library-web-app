package ru.otus.librarywebapp.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import reactor.core.publisher.Flux;

public interface PageableFindAll<T> {
    @Query("{ id: { $exists: true }}")
    Flux<T> findAll(Pageable pageable);
}
