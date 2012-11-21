package com.jglitter.web;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.Tweets;
import com.jglitter.domain.User;
import com.jglitter.domain.UserNotFoundException;
import com.jglitter.persistence.repo.TweetRepository;
import com.jglitter.persistence.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Encapsulates RESTful web service endpoints for working with tweets.
 */
@Controller
public class TweetWebService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new tweet by issuing an HTTP POST to {host}/javainterview/tweet, returning a 200 on a successful creation.
     *
     * @param tweet the tweet included in the request body.
     * @return The created tweet, marshalled into the response body.
     * @throws UserNotFoundException When the user specified as the author of the tweet is not found in the system, resolving as a HTTP return code 404.
     */
    @Transactional
    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
    public Tweet createTweet(@RequestBody Tweet tweet) {
        final User author = userRepository.findByUuid(tweet.getAuthor().getUuid());
        if (author == null) {
            throw new UserNotFoundException(tweet.getAuthor().getUuid());
        }
        return tweetRepository.persist(new Tweet(author, tweet.getMessage()));
    }

    /**
     * Deletes a tweet by issuing an HTTP DELETE to {host}/javainterview/tweet/{tweetUuid}, return a 200 on a successful deletion.
     *
     * @param tweetUuid the UUID of the tweet to delete.
     */
    @Transactional
    @RequestMapping(value = "/tweet/{tweetUuid}", method = RequestMethod.DELETE)
    public void deleteTweet(@PathVariable String tweetUuid) {
        Tweet tweetToDelete = tweetRepository.findByUuid(tweetUuid);
        if (tweetToDelete != null) {
            tweetRepository.delete(tweetToDelete);
        }
    }

    /**
     * Finds all the tweets authored by the specified user, by issing an HTTP GET to {host}/javainterview/author/{authorUuid}/tweets.
     *
     * @param authorUuid the UUID of the user who authored the tweets
     * @return All the user's tweets, marshalled into the response body
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/author/{authorUuid}/tweets", method = RequestMethod.GET)
    public Tweets findTweetsAuthoredBy(@PathVariable String authorUuid) {
        final User user = userRepository.findByUuid(authorUuid);
        if (user == null) {
            throw new UserNotFoundException(authorUuid);
        }
        return new Tweets(tweetRepository.findAllByAuthor(user));
    }
}
