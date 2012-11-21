package com.jglitter.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement
public class Users {

    @XmlElement
    private Collection<User> users = new ArrayList<User>();

    public Users(Collection<User> users) {
        this();
        this.users = users;
    }

    protected Users(){
    }

    public boolean contains(User user) {
        return users.contains(user);
    }
}
