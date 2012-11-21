package com.jglitter.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple JAXB XML object that wraps zero to many users.
 */
@XmlRootElement
public class Users {

    @XmlElement
    private Collection<User> users = new ArrayList<User>();

    /**
     * Creates a new object.
     *
     * @param users the users to be enapsulated
     */
    public Users(Collection<User> users) {
        this();
        this.users = users;
    }

    /**
     * Empty constructor necessary for JAXB.
     */
    protected Users() {
    }

    /**
     * @param user the user to search for
     * @return <code>true</code> if the user is found
     */
    public boolean contains(User user) {
        return users.contains(user);
    }
}
