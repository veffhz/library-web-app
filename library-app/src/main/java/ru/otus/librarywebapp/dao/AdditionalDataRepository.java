package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.AdditionalData;

import java.util.List;

public interface AdditionalDataRepository extends MongoRepository<AdditionalData, String>, PageableFindAll<AdditionalData> {
    List<AdditionalData> findByBookId(String bookId);
    List<AdditionalData> deleteByBookId(String bookId);
}
