package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class UserApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user")
    public Mono<List<Object>> getUser() {
        log.info("get current user");

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(list -> Arrays.asList(list.getName(), list.getDetails(), list.getPrincipal(), list.getAuthorities()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/user")
    public Mono<List<Object>> create(@RequestBody String data) {
        log.info("receive data {}", data);
        // TODO create user
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(sec -> Arrays.asList(sec.getName(), sec.getAuthorities(), data));
    }

    // TODO implement

}
