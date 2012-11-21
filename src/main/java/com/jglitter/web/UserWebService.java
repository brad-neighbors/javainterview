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

@Controller
public class UserWebService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userRepository.persist(user);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Users getUsers() {
        return new Users(userRepository.findAll());
    }

    @Transactional
    @RequestMapping(value = "/allusers", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
