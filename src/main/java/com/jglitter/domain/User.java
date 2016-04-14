package com.jglitter.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.UUID;

/**
 * Encapsulates a user.
 * This entity can be persisted via JPA and marshalled over the wire as JSON.
 * A user is unique based on their email address.
 */
@Entity
public class User extends HasPrimaryKey {

    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @Column(unique = true)
    private UUID uuid;

    /**
     * Creates a new user.
     *
     * @param email the user's email address
     * @param name  the user's chosen name
     */
    @JsonCreator
    public User(@JsonProperty("email") final String email, @JsonProperty("name") final String name) {
        this();
        this.email = email;
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Empty constructor necessary for persistence.
     */
    protected User() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
