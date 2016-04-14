package com.jglitter.web;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.Tweets;
import com.jglitter.domain.User;
import com.jglitter.domain.UserNotFoundException;
import com.jglitter.persistence.TweetRepository;
import com.jglitter.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Encapsulates RESTful web service endpoints for working with tweets.
 */
@Component
@Path("/tweets")
public class TweetWebService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetWebService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new tweet by issuing an HTTP POST to {host}/api/tweets, returning a 200 on a successful creation.
     *
     * @param tweet the tweet included in the request body.
     * @return The created tweet, marshalled into the response body.
     * @throws UserNotFoundException When the user specified as the author of the tweet is not found in the system, resolving as a HTTP return code 404.
     */
    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Tweet createTweet(Tweet tweet) {
        final Optional<User> author = userRepository.findByUuid(tweet.getAuthor().getUuid());
        if (!author.isPresent()) {
            throw new UserNotFoundException(tweet.getAuthor().getUuid());
        }
        return tweetRepository.save(new Tweet(author.get(), tweet.getMessage()));
    }

    /**
     * Deletes a tweet by issuing an HTTP DELETE to {host}/api/tweets/{tweetUuid}, return a 200 on a successful deletion.
     *
     * @param tweetUuid the UUID of the tweet to delete.
     */
    @Transactional
    @DELETE
    @Path("/{tweetUuid}")
    public void deleteTweet(@PathParam("tweetUuid") UUID tweetUuid) {
        Optional<Tweet> tweetToDelete = tweetRepository.findByUuid(tweetUuid);
        if (!tweetToDelete.isPresent()) {
            tweetRepository.delete(tweetToDelete.get());
        }
    }

    /**
     * Finds all the tweets authored by the specified user, by issuing an HTTP GET to {host}/api/tweets/byAuthor/{authorUuid}.
     *
     * @param authorUuid the UUID of the user who authored the tweets
     * @return All the user's tweets, marshalled into the response body
     */
    @Transactional(readOnly = true)
    @GET
    @Path("/byAuthor/{authorUuid}")
    @Produces("application/json")
    public Tweets findTweetsAuthoredBy(@PathParam("authorUuid") UUID authorUuid) {
        final Optional<User> user = userRepository.findByUuid(authorUuid);
        if (!user.isPresent()) {
            throw new UserNotFoundException(authorUuid);
        }
        return new Tweets(tweetRepository.findByAuthor(user.get()));
    }

    /**
     * Finds all tweets matching the search term by issuing an HTTP GET to {host}/api/tweets/search?term={searchTerm(s)}.
     *
     * @param searchTerm the word(s)? to search for
     * @return All the matching tweets.
     */
    @Transactional(readOnly = true)
    @GET
    @Path("/search")
    @Produces("application/json")
    public Tweets search(@QueryParam("term") String searchTerm) {
        // Implement an in-memory pure java search by first loading all tweets from the database.
        Collection<Tweet> all = tweetRepository.findAll();
        // TODO: implement an in-memory search

        return new Tweets(all);
    }
}
