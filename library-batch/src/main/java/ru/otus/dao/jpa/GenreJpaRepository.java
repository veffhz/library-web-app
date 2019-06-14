package ru.otus.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
    List<Genre> findByGenreName(String genreName);
}
