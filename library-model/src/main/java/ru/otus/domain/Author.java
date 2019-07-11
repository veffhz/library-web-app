package ru.otus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document(collection = "authors")
@Entity
@Table(name = "authors")
public class Author {

    public static final String N_A = "n/a";

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    private String firstName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @NotNull
    private LocalDate birthDate;

    @Indexed
    @NotBlank
    private String lastName;

    public Author(String firstName, LocalDate birthDate, String lastName) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", Objects.toString(firstName, ""),
                Objects.toString(lastName, ""));
    }

    @JsonIgnoreProperties
    public boolean isAvailable() {
        return !N_A.equals(lastName);
    }

}
