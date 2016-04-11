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

/**
 * Encapsulates RESTful web service endpoints for working with tweets.
 */
@Component
@Path("/tweet")
public class TweetWebService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetWebService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new tweet by issuing an HTTP POST to {host}/javainterview/api/tweet, returning a 200 on a successful creation.
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
        final User author = userRepository.findByUuid(tweet.getAuthor().getUuid());
        if (author == null) {
            throw new UserNotFoundException(tweet.getAuthor().getUuid());
        }
        return tweetRepository.save(new Tweet(author, tweet.getMessage()));
    }

    /**
     * Deletes a tweet by issuing an HTTP DELETE to {host}/javainterview/api/tweet/{tweetUuid}, return a 200 on a successful deletion.
     *
     * @param tweetUuid the UUID of the tweet to delete.
     */
    @Transactional
    @DELETE
    @Path("/{tweetUuid}")
    public void deleteTweet(@PathParam("tweetUuid") String tweetUuid) {
        Tweet tweetToDelete = tweetRepository.findByUuid(tweetUuid);
        if (tweetToDelete != null) {
            tweetRepository.delete(tweetToDelete);
        }
    }

    /**
     * Finds all the tweets authored by the specified user, by issing an HTTP GET to {host}/javainterview/api/author/{authorUuid}/tweets.
     *
     * @param authorUuid the UUID of the user who authored the tweets
     * @return All the user's tweets, marshalled into the response body
     */
    @Transactional(readOnly = true)
    @GET
    @Path("/byAuthor/{authorUuid}")
    @Produces("application/json")
    public Tweets findTweetsAuthoredBy(@PathParam("authorUuid") String authorUuid) {
        final User user = userRepository.findByUuid(authorUuid);
        if (user == null) {
            throw new UserNotFoundException(authorUuid);
        }
        return new Tweets(tweetRepository.findByAuthor(user));
    }
}
