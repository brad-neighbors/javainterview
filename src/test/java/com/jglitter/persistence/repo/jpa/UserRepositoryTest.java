package com.jglitter.persistence.repo.jpa;


import com.jglitter.domain.User;
import com.jglitter.persistence.Config;
import com.jglitter.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * These tests act directly against the Repository beans, meaning it will instantiate them, an in-memory database (h2),
 * running Liquibase to prepare the schema, etc.  These are essentially tests of user persistence: queries and inserts, etc.
 */
@Test
@ContextConfiguration(classes = {Config.class})
public class UserRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void canPersistAUser() {
        User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        assertNotNull(johnDoe.getId(), "Newly persisted user did not have primary key.");

        Optional<User> retrieved = userRepository.findByUuid(johnDoe.getUuid());
        assertEquals(retrieved.get().getEmail(), "john@doe.com", "Email address");
        assertEquals(retrieved.get().getName(), "John Doe", "Name");
        assertEquals(retrieved.get().getUuid(), johnDoe.getUuid(), "UUID");
   }

    @Test
    public void canFindAllUsers() {
        final User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        final User janeDoe = userRepository.save(new User("jane@doe.com", "Jane Doe"));
        final Collection<User> all = userRepository.findAll();
        assertTrue(all.contains(johnDoe));
        assertTrue(all.contains(janeDoe));
    }

    @Test
    public void canFindUserByUuid() {
        final User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        final User janeDoe = userRepository.save(new User("jane@doe.com", "Jane Doe"));

        Optional<User> johnDoeRetrieved = userRepository.findByUuid(johnDoe.getUuid());
        assertTrue(johnDoe.equals(johnDoeRetrieved.get()), "Found John Doe");

        Optional<User> janeDoeRetrieved = userRepository.findByUuid(janeDoe.getUuid());
        assertTrue(janeDoe.equals(janeDoeRetrieved.get()), "Found Jane Doe");

        assertFalse(userRepository.findByUuid(UUID.randomUUID()).isPresent(),
                "Mistakenly found a user by uuid not in database");
    }

    @Test
    public void cannotPersistMoreThanOneUserWithSameEmailAddress() {
        final User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        assertNotNull(johnDoe.getId(), "Newly persisted user did not have primary key.");

        try {
            userRepository.save(new User("john@doe.com", "Jim Doe"));
            fail("Should have disallowed persisting second user with duplicated email");
        } catch (Exception e){}
    }
}
