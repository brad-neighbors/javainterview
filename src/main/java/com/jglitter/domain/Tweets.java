package com.jglitter.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A simple JSON wrapping object that encapsulates zero to many tweets.
 */
public class Tweets implements Iterable<Tweet> {

    private Collection<Tweet> tweets = new ArrayList<Tweet>();

    /**
     * Creates a new Tweets object.
     *
     * @param tweets the tweets this wrapper encapsulates
     */
    @JsonCreator
    public Tweets(@JsonProperty Collection<Tweet> tweets) {
        this.tweets = tweets;
    }

    /**
     * @param tweet the tweet to search for
     * @return <code>true</code> if the tweet is found in these tweets
     */
    public boolean contains(Tweet tweet) {
        return tweets.contains(tweet);
    }

    @Override
    public Iterator<Tweet> iterator() {
        return tweets.iterator();
    }
}
