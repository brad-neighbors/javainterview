package com.jglitter.persistence.repo;

import com.jglitter.domain.User;

import java.util.List;

public interface UserRepository {

    User persist(User user);

    List<User> findAll();

    User findByUuid(String id);

    void deleteAll();
}
