package ru.otus.dao.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookJpaRepository extends CrudRepository<Book, String> {
    List<Book> findByBookName(String bookName);
    List<Book> findByBookNameContaining(String bookName);
    @EntityGraph("bookGraph")
    List<Book> findAll();
}
