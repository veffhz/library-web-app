package ru.otus.librarywebapp.endpoints;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Link {

    @Getter
    private final String href;

    @Getter
    private boolean templated;

}
