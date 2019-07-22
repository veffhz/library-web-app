package ru.otus.librarywebapp.endpoints;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Link {

    @Getter
    private final String href;

    @Getter
    private boolean templated;
}
