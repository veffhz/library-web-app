package ru.otus.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "additionalData")
public class AdditionalData {

    private final List<String> items;

    @DBRef
    private Book book;

    public AdditionalData() {
        this.items = new ArrayList<>();
    }

    public boolean isNotEmpty() {
        return !items.isEmpty();
    }
}
