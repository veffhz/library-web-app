package ru.otus.librarywebapp.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import ru.otus.domain.Role;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {
    Mono<Role> findByRoleName(String roleName);
}
