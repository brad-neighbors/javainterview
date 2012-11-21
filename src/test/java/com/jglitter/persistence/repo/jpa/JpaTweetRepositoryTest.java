package com.jglitter.persistence.repo.jpa;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;
import com.jglitter.persistence.repo.TweetRepository;
import com.jglitter.persistence.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.*;

@Test
@ContextConfiguration({"classpath:application-config.xml"})
public class JpaTweetRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TweetRepository tweetRepo;

    @Autowired
    private UserRepository userRepo;

    @Test
    public void canPersistATweet() {
        User author = userRepo.persist(new User("jane@doe.com", "Jane Doe"));

        Tweet tweet = tweetRepo.persist(new Tweet(author, "I have a common name"));
        assertNotNull(tweet.getId(), "Persisted tweet was not assigned primary key.");

        Tweet retrieved = tweetRepo.findById(tweet.getId());
        assertEquals(retrieved, tweet, "Tweet retrieved by primary key not same as persisted tweet");
    }

    @Test
    public void canFindAllTweetsByAuthor() {
        User elaine = userRepo.persist(new User("elaine@bennis.com", "Elaine Benes"));
        User george = userRepo.persist(new User("george@castanza.com", "George Costanza"));

        Tweet elainesTweet = tweetRepo.persist(new Tweet(elaine, "I'm an awesome dancer."));

        tweetRepo.persist(new Tweet(george, "George is getting angry."));
        tweetRepo.persist(new Tweet(george, "I wish I was an architect."));
        tweetRepo.persist(new Tweet(george, "I'm going bald."));

        Collection<Tweet> tweets = tweetRepo.findAllByAuthor(elaine);
        assertEquals(1, tweets.size(), "Tweet count of tweets by author not accurate");
        assertTrue(tweets.contains(elainesTweet), "Did not find expected tweet by author");
    }
}
