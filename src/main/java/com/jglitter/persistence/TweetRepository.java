package com.jglitter.persistence;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Specifies persistence operations for tweets.
 */
@Repository
public interface TweetRepository extends CrudRepository<Tweet, Integer> {

    /**
     * Finds all the tweets by a specified author.
     *
     * @param author who authored tweets
     * @return All the author's tweets, or an empty collection when none exist.
     */
    Collection<Tweet> findByAuthor(User author);

    /**
     * Finds a tweet by its UUID.
     *
     * @param uuid the tweet uuid identifier
     * @return The optional tweet.
     */
    Optional<Tweet> findByUuid(UUID uuid);

    /**
     * Finds all tweets in the DB.
     *
     * @return All the tweets.
     */
    @Query("select t from Tweet t")
    Collection<Tweet> findAll();
}
