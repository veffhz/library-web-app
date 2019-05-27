package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String>, ReactiveUserDetailsService {
    Mono<UserDetails> findByUsername(String username);
}
