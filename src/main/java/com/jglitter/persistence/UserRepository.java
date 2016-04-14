package com.jglitter.persistence;

import com.jglitter.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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
    Optional<User> findByUuid(UUID uuid);

    /**
     * Finds all the users.
     *
     * @return All the users in the database.
     */
    @Query("select u from User u")
    Collection<User> findAll();
}
