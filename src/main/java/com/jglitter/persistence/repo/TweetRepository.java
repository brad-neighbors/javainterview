package com.jglitter.persistence.repo;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;

import java.util.Collection;

public interface TweetRepository {

    Tweet persist(Tweet tweet);

    Tweet findById(Integer id);

    Collection<Tweet> findAllByAuthor(User author);

    void delete(Tweet tweet);

    Tweet findByUuid(String tweetUuid);
}
