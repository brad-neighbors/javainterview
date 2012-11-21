package com.jglitter.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple JAXB XML wrapping object that encapsulates zero to many tweets.
 */
@XmlRootElement
public class Tweets {

    @XmlElement
    private Collection<Tweet> tweets = new ArrayList<Tweet>();

    /**
     * Creates a new Tweets object.
     *
     * @param tweets the tweets this wrapper encapsulates
     */
    public Tweets(Collection<Tweet> tweets) {
        this();
        this.tweets = tweets;
    }

    /**
     * Empty constructor necessary for JAXB.
     */
    protected Tweets() {
    }

    /**
     * @param tweet the tweet to search for
     * @return <code>true</code> if the tweet is found in these tweets
     */
    public boolean contains(Tweet tweet) {
        return tweets.contains(tweet);
    }
}
