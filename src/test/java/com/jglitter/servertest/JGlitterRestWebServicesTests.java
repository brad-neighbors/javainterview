package com.jglitter.servertest;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.Tweets;
import com.jglitter.domain.User;
import com.jglitter.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class JGlitterRestWebServicesTests extends AbstractTests {

    @Autowired
    private RestTemplate restTemplate;

    @AfterMethod
    public void deleteAllUsers() {
        restTemplate.delete(wsRoot() + "/allusers");
    }

    @Test
    void canCreateAUser() {
        User johnDoe = restTemplate.postForEntity(wsRoot() + "/user", new User("john@doe.com", "JohnDoe"), User.class).getBody();
        Users allUsers = restTemplate.getForEntity(wsRoot() + "/user", Users.class).getBody();
        assertTrue(allUsers.contains(johnDoe), "All users didn't include newly added user.");
    }

    @Test
    void userCanAuthorATweet() {
        User author = restTemplate.postForEntity(wsRoot() + "/user", new User("auth@or.com", "JohnDoe"), User.class).getBody();
        Tweet tweet = restTemplate.postForEntity(wsRoot() + "/tweet", new Tweet(author, "This is my first tweet!"), Tweet.class).getBody();
        Tweets tweets = restTemplate.getForEntity(wsRoot() + "/author/" + author.getUuid() + "/tweets", Tweets.class).getBody();
        assertTrue(tweets.contains(tweet), "All tweets by the author includes the new tweet.");
    }

    @Test
    void unknownUserCannotAuthorATweet() {
        User unknownAuthor = new User("sneaky@bastard.com", "sneaky");
        try {
            restTemplate.postForEntity(wsRoot() + "/tweet", new Tweet(unknownAuthor, "This is my first tweet!"), Tweet.class).getBody();
            fail("Should have failed posting a tweet by an unknown author.");
        } catch (HttpClientErrorException exception) {
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Error code wasn't NOT_FOUND");
        }
    }

    private String wsRoot() {
        // putting this here now because I removed the URL helper class
        return "http://localhost:8080/jglitter/ws";
    }
}
