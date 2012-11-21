package com.jglitter.persistence.repo;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;

import java.util.Collection;

/**
 * Specifies persistence operations for tweets.
 */
public interface TweetRepository {

    /**
     * Persists a new tweet.
     *
     * @param tweet the tweet to save
     * @return The persisted tweet.
     */
    Tweet persist(Tweet tweet);

    /**
     * Finds a tweet by its primary key.
     *
     * @param id the tweet's primary key
     * @return The tweet, or <code>null</code> if not found.
     */
    Tweet findById(Integer id);

    /**
     * Finds all the tweets by a specified author.
     *
     * @param author who authored tweets
     * @return All the author's tweets, or an empty collection when none exist.
     */
    Collection<Tweet> findAllByAuthor(User author);

    /**
     * Deletes a tweet.
     *
     * @param tweet the tweet to delete
     */
    void delete(Tweet tweet);

    /**
     * Finds a tweet by its UUID.
     *
     * @param tweetUuid the tweet uuid identifier
     * @return The tweet, or <code>null</code> if not found.
     */
    Tweet findByUuid(String tweetUuid);
}
