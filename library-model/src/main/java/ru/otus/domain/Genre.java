package ru.otus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public static final String N_A = "n/a";

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

    @JsonIgnore
    public boolean isAvailable() {
        return !N_A.equals(genreName);
    }

}
