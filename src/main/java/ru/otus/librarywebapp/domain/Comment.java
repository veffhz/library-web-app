package ru.otus.librarywebapp.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString(exclude = "book")
@Document(collection = "comments")
@EqualsAndHashCode
public class Comment {

    @Id
    private String id;
    @Setter
    @DBRef
    private Book book;
    private String author;
    private Date date;
    private String content;

    public Comment(String author, Date date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }

}
