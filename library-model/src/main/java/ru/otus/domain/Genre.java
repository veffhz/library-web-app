package ru.otus.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Document(collection = "genres")
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Indexed
    @NotBlank
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

}
