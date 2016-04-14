package com.jglitter.domain;

import javax.ws.rs.NotFoundException;

import java.util.UUID;

import static java.lang.String.format;

/**
 * Encapsulates a runtime exception when an operation occurred expecting a user that was not found in the system.
 * Specifies a HTTP return code of 404 when thrown and handled by the default exception handler for RESTful web services.
 */
public class UserNotFoundException extends NotFoundException {

    private final UUID userId;

    /**
     * Creates a new exception.
     *
     * @param userUuid the UUID of the user that was not found
     */
    public UserNotFoundException(final UUID userUuid) {
        this.userId = userUuid;
    }

    @Override
    public String toString() {
        return format("User [%s] not found", userId);
    }
}
