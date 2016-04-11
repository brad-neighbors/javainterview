package com.jglitter.persistence.repo.jpa;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;
import com.jglitter.persistence.Config;
import com.jglitter.persistence.TweetRepository;
import com.jglitter.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.*;

/**
 * These tests act directly against the Repository beans, meaning it will instantiate them, an in-memory database (h2),
 * running Liquibase to prepare the schema, etc.  These are essentially tests of tweet persistence: queries and inserts, etc.
 */
@Test
@ContextConfiguration(classes = {Config.class})
public class JpaTweetRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TweetRepository tweetRepo;

    @Autowired
    private UserRepository userRepo;

    @Test
    public void canPersistATweet() {
        User author = userRepo.save(new User("jane@doe.com", "Jane Doe"));

        Tweet tweet = tweetRepo.save(new Tweet(author, "I have a common name"));
        assertNotNull(tweet.getId(), "Persisted tweet was not assigned primary key.");

        Tweet retrieved = tweetRepo.findOne(tweet.getId());
        assertEquals(retrieved, tweet, "Tweet retrieved by primary key not same as persisted tweet");
    }

    @Test
    public void canFindAllTweetsByAuthor() {
        User elaine = userRepo.save(new User("elaine@bennis.com", "Elaine Benes"));
        User george = userRepo.save(new User("george@castanza.com", "George Costanza"));

        Tweet elainesTweet = tweetRepo.save(new Tweet(elaine, "I'm an awesome dancer."));

        tweetRepo.save(new Tweet(george, "George is getting angry."));
        tweetRepo.save(new Tweet(george, "I wish I was an architect."));
        tweetRepo.save(new Tweet(george, "I'm going bald."));

        Collection<Tweet> tweets = tweetRepo.findByAuthor(elaine);
        assertEquals(1, tweets.size(), "Tweet count of tweets by author not accurate");
        assertTrue(tweets.contains(elainesTweet), "Did not find expected tweet by author");
    }
}
