package com.jglitter.servertest;

import com.jglitter.Config;
import com.jglitter.domain.Tweet;
import com.jglitter.domain.Tweets;
import com.jglitter.domain.User;
import com.jglitter.domain.Users;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static javax.ws.rs.client.Entity.json;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * These tests start up an embedded Tomcat server with the Jglitter application deployed, and exercise REST calls
 * against the REST api using JaxRS client.
 */
@Test
public class RestApiTests {

    private static final boolean PRINT_REST_ENTITY = true;

    protected static ConfigurableApplicationContext context;
    protected static WebTarget webTarget;

    @BeforeSuite
    public void prepareRestTestingAgainstEmbeddedServer()
            throws Exception {
        startServer();

        Client client = setupRestClient();
        webTarget = client.target("http://localhost:8080/api");
    }

    private void startServer()
            throws InterruptedException, java.util.concurrent.ExecutionException,
                   java.util.concurrent.TimeoutException {
        Future<ConfigurableApplicationContext> future =
                Executors.newSingleThreadExecutor().submit(new Callable<ConfigurableApplicationContext>() {
                    @Override
                    public ConfigurableApplicationContext call() throws Exception {
                        return SpringApplication.run(Config.class);
                    }
                });

        context = future.get(60, TimeUnit.SECONDS);
    }

    private Client setupRestClient() {
        ClientConfig clientConfig = new ClientConfig();
        LoggingFilter loggingFilter = new LoggingFilter(Logger.getLogger(this.getClass().getName()), PRINT_REST_ENTITY);
        clientConfig.register(loggingFilter);
        return ClientBuilder.newClient(clientConfig).register(JacksonFeature.class);
    }

    @AfterSuite(alwaysRun = true)
    public void stopEmbeddedServer() throws Exception {
        if (context != null) {
            context.close();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllUsers() {
        webTarget.path("/user").request().delete();
    }

    @Test
    public void canCreateAUser() {
        User johnDoe = webTarget.path("/user").request().post(json(new User("john@doe.com", "JohnDoe")), User.class);
        Users allUsers = webTarget.path("/user").request().get(Users.class);
        assertTrue(allUsers.contains(johnDoe), "All users didn't include newly added user.");
    }

    @Test
    public void userCanAuthorATweet() {
        User author = webTarget.path("/user").request().post(json(new User("auth@or.com", "JohnDoe")), User.class);
        Tweet tweet = webTarget.path("/tweet").request().post(json(new Tweet(author, "This ")), Tweet.class);
        Tweets tweets = webTarget.path("/tweet/byAuthor/" + author.getUuid()).request().get(Tweets.class);
        assertTrue(tweets.contains(tweet), "All tweets by the author includes the new tweet.");
    }

    @Test
    public void unknownUserCannotAuthorATweet() {
        User unknownAuthor = new User("sneaky@bastard.com", "sneaky");
        try {
            webTarget.path("/tweet").request().post(json(new Tweet(unknownAuthor, "This is my first tweet!")), Tweet.class);
            fail("Should have failed posting a tweet by an unknown author.");
        } catch (NotFoundException exception) {
        }
    }

    @Test
    public void tweetNotLongerThan10Char() {
        try {
            User author = webTarget.path("/user").request().post(json(new User("auth@or.com", "JohnDoe")), User.class);
            webTarget.path("/tweet").request().post(json(new Tweet(author, "This is my first tweet!")), Tweet.class);
            fail("Should have less than 10 charactoers.");
        } catch (BadRequestException exception) {
        }
    }

    @Test
    public void canSearchForTweetsByKeyword() {
        User author = webTarget.path("/user").request().post(json(new User("auth@or.com", "JohnDoe")), User.class);
        Tweet red = webTarget.path("/tweet").request().post(json(new Tweet(author, "Red")), Tweet.class);
        Tweet blue = webTarget.path("/tweet").request().post(json(new Tweet(author, "Blue")), Tweet.class);
        Tweet usa = webTarget.path("/tweet").request().post(json(new Tweet(author, "Red Blue")), Tweet.class);
        Tweets tweets = webTarget.path("/tweet/byKeyword/" + "Red").request().get(Tweets.class);
        assertTrue(tweets.contains(red));
        assertTrue(tweets.contains(usa));
        assertTrue(tweets.size() == 2, "Size of tweets");
    }
}
