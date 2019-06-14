package ru.otus.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorJpaRepository extends JpaRepository<Author, String> {
    List<Author> findAll();
    List<Author> findByLastName(String lastName);
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
