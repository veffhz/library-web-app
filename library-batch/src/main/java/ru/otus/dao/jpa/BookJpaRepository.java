package ru.otus.dao.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, String> {
    @EntityGraph("bookGraph")
    List<Book> findAll();
}
