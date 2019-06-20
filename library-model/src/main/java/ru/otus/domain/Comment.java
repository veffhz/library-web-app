package ru.otus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(exclude = "book")
@Document(collection = "comments")
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @DBRef
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
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
