package com.jglitter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

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

    public User(final String email, final String name) {
        this();
        this.email = email;
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    protected User() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

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
