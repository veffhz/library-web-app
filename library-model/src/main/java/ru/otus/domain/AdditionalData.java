package ru.otus.domain;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "additionalData")
public class AdditionalData {

    @Id
    private String id;

    private List<String> items;

    @DBRef
    @JsonIgnoreProperties({"author", "genre", "publishDate", "language", "publishingHouse", "city", "isbn"})
    private Book book;

    public AdditionalData() {
        this.items = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !items.isEmpty();
    }
}