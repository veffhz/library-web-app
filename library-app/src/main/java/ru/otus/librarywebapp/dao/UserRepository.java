package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import ru.otus.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByUsername(String username);
}
