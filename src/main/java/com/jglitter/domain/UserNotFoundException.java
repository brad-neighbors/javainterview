package com.jglitter.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

/**
 * Encapsulates a runtime exception when an operation occurred expecting a user that was not found in the system.
 * Specifies a HTTP return code of 404 when thrown and handled by the default exception handler for RESTful web services.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final String userId;

    /**
     * Creates a new exception.
     *
     * @param userUuid the UUID of the user that was not found
     */
    public UserNotFoundException(final String userUuid) {
        this.userId = userUuid;
    }

    @Override
    public String toString() {
        return format("User [%s] not found", userId);
    }
}
