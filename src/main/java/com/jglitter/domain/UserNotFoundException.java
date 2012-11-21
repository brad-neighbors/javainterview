package com.jglitter.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final String userId;

    public UserNotFoundException(final String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return format("User [%s] not found", userId);
    }
}
