package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class UserApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user")
    public List<Object> getUser() {
        log.info("get current user");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return Arrays.asList(authentication.getName(), authentication.getDetails(),
                authentication.getPrincipal(), authentication.getAuthorities());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/user")
    public List<Object> create(@RequestBody String data) {
        log.info("receive data {}", data);
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return Arrays.asList(authentication.getName(), authentication.getAuthorities(), data);
    }

    // TODO implement

}
