package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import ru.otus.domain.AdditionalData;

public interface AdditionalDataRepository extends ReactiveMongoRepository<AdditionalData, String>, PageableFindAll<AdditionalData> {
    Flux<AdditionalData> findByBookId(String bookId);
    Flux<AdditionalData> deleteByBookId(String bookId);
}
