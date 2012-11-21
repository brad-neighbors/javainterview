package com.jglitter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * Encapsulates a user.
 * This entity can be persisted via JPA and marshalled over the wire as XML using JAXB.
 * A user is unique based on their email address.
 */
@Entity
@XmlRootElement
public class User extends HasPrimaryKey {

    @Column
    @XmlElement
    private String email;

    @Column
    @XmlElement
    private String name;

    @Column
    @XmlElement
    private String uuid;

    /**
     * Creates a new user.
     *
     * @param email the user's email address
     * @param name  the user's chosen name
     */
    public User(final String email, final String name) {
        this();
        this.email = email;
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    /**
     * Empty constructor necessary for javadoc.
     */
    protected User() {
    }

    /**
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The name the user chose.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The user's unique ID, used as an identifier at a business level and web services.
     */
    public String getUuid() {
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
