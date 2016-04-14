package com.jglitter.web;

import com.jglitter.domain.User;
import com.jglitter.domain.Users;
import com.jglitter.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Encapsulates RESTful web service endpoints for working with users.
 */
@Component
@Path("/users")
public class UserWebService {

    private UserRepository userRepository;

    @Autowired
    public UserWebService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user by issuing an HTTP POST to {host}/api/users, returning a 200 on successful creation.
     *
     * @param user the user to create, sent in the request body as XML
     * @return The user created, sent in the response body.
     */
    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Gets all the users by issuing an HTTP GET to {host}/api/users, returning a 200 on successful retrieval.
     *
     * @return All the users, sent in the response body as XML.
     */
    @Transactional(readOnly = true)
    @GET
    @Produces("application/json")
    public Users getUsers() {
        return new Users(userRepository.findAll());
    }

    /**
     * Deletes all the users by issuing an HTTP DELETE to {host}/api/users, returning a 200 on successful deletion.
     */
    @Transactional
    @DELETE
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
