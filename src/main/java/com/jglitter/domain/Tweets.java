package com.jglitter.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement
public class Tweets {

    @XmlElement
    private Collection<Tweet> tweets = new ArrayList<Tweet>();

    public Tweets(Collection<Tweet> tweets) {
        this();
        this.tweets = tweets;
    }

    protected Tweets() {
    }

    public boolean contains(Tweet tweet) {
        return tweets.contains(tweet);
    }
}
