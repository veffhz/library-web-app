package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import reactor.core.publisher.Mono;

import ru.otus.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<UserDetails> findByUsername(String username);
}
