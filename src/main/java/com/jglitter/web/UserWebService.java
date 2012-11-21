package com.jglitter.web;

import com.jglitter.domain.User;
import com.jglitter.domain.Users;
import com.jglitter.persistence.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Encapsulates RESTful web service endpoints for working with users.
 */
@Controller
public class UserWebService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user by issuing an HTTP POST to {host}/{webAppContext}/user, returning a 200 on successful creation.
     *
     * @param user the user to create, sent in the request body as XML
     * @return The user created, sent in the response body.
     */
    @Transactional
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userRepository.persist(user);
    }

    /**
     * Gets all the users by issuing an HTTP GET to {host}/{webAppContext}/user, returning a 200 on successful retrieval.
     *
     * @return All the users, sent in the response body as XML.
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Users getUsers() {
        return new Users(userRepository.findAll());
    }

    /**
     * Deletes all the users by issuing an HTTP GET to {host}/{webAppContext}/user, returning a 200 on successful deletion.
     */
    @Transactional
    @RequestMapping(value = "/allusers", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
