package com.jglitter.persistence.repo;

import com.jglitter.domain.User;

import java.util.List;

/**
 * Specifies persistence operations for users.
 */
public interface UserRepository {

    /**
     * Persists a new user.
     *
     * @param user the user to save
     * @return The newly persisted user.
     */
    User persist(User user);

    /**
     * Finds all the users.
     *
     * @return All the users.
     */
    List<User> findAll();

    /**
     * Finds a user by its UUID.
     *
     * @param uuid the user's UUID identifier.
     * @return The found user, or <code>null</code> when not found.
     */
    User findByUuid(String uuid);

    /**
     * Deletes all the persisted users in the repository.
     */
    void deleteAll();
}
