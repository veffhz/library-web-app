package ru.otus.librarywebapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(exclude = "book")
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    @Setter
    @DBRef
    private Book book;
    @NotBlank
    private String author;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    @NotBlank
    private String content;

    public Comment(String author, Date date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }

}
