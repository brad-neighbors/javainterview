package com.jglitter.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

/**
 * A simple JSON object that wraps zero to many users.
 */
public class Users implements Iterable<User>{

    private Collection<User> users = new ArrayList<User>();

    /**
     * Creates a new object.
     *
     * @param users the users to be encapsulated
     */
    @JsonCreator
    public Users(@JsonProperty Collection<User> users) {
        this.users = users;
    }

    /**
     * @param user the user to search for
     * @return <code>true</code> if the user is found
     */
    public boolean contains(User user) {
        return users.contains(user);
    }


    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }
}
