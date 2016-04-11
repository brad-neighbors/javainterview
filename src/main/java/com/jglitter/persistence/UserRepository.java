package com.jglitter.persistence;

import com.jglitter.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Specifies persistence operations for users.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Finds a user by its UUID.
     *
     * @param uuid the user's UUID identifier.
     * @return The found user, or <code>null</code> when not found.
     */
    User findByUuid(String uuid);

    @Query("select u from User u")
    Collection<User> findAll();
}
