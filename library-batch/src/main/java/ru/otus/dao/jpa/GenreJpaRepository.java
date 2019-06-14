package ru.otus.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreJpaRepository extends JpaRepository<Genre, String> {
    List<Genre> findAll();
    List<Genre> findByGenreName(String genreName);
    Optional<Genre> findOneByGenreName(String genreName);
}
