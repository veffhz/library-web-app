package ru.otus.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorJpaRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();
    List<Author> findByLastName(String lastName);
}
