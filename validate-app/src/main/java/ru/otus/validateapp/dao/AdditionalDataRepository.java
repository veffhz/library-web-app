package ru.otus.validateapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.domain.AdditionalData;

import java.util.List;

public interface AdditionalDataRepository extends MongoRepository<AdditionalData, String> {
    List<AdditionalData> findByBookId(String bookId);
}
