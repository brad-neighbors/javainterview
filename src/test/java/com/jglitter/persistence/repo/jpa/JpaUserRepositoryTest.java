package com.jglitter.persistence.repo.jpa;


import com.jglitter.domain.User;
import com.jglitter.persistence.Config;
import com.jglitter.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
@ContextConfiguration(classes = {Config.class})
public class JpaUserRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void canPersistAUser() {
        User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        assertNotNull(johnDoe.getId(), "Newly persisted user did not have primary key.");

        User retrieved = userRepository.findByUuid(johnDoe.getUuid());
        assertEquals(retrieved, johnDoe, "Retrieved user did not match newly inserted user.");
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
        assertTrue(johnDoe.equals(userRepository.findByUuid(johnDoe.getUuid())));
        assertTrue(janeDoe.equals(userRepository.findByUuid(janeDoe.getUuid())));
        assertNull(userRepository.findByUuid("NotFound"), "Mistakenly found a user by uuid not in database");
    }

    @Test
    public void cannotPersistMoreThanOneUserWithSameEmailAddress() {
        final User johnDoe = userRepository.save(new User("john@doe.com", "John Doe"));
        assertNotNull(johnDoe.getId(), "Newly persisted user did not have primary key.");

        try {
            userRepository.save(new User("john@doe.com", "Jim Doe"));
            fail("Should have disallowed persisting second user with duplicated email");
        } catch (Exception e){

        }
    }
}
