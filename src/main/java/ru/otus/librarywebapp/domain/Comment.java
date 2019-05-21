package ru.otus.librarywebapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(exclude = "book")
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    @DBRef
    private Book book;
    @NotBlank
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @CreatedDate
    private LocalDateTime date;
    @NotBlank
    private String content;

    public Comment(String author, LocalDateTime date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }

}
