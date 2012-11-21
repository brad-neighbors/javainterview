package com.jglitter.web;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.Tweets;
import com.jglitter.domain.User;
import com.jglitter.domain.UserNotFoundException;
import com.jglitter.persistence.repo.TweetRepository;
import com.jglitter.persistence.repo.UserRepository;
import com.jglitter.util.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TweetWebService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Clock clock;

    @Transactional
    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
    public Tweet createTweet(@RequestBody Tweet tweet) {
        final User author = userRepository.findByUuid(tweet.getAuthor().getUuid());
        if (author == null) {
            throw new UserNotFoundException(tweet.getAuthor().getUuid());
        }
        return tweetRepository.persist(new Tweet(author, tweet.getMessage()));
    }

    @Transactional
    @RequestMapping(value = "/tweet/{tweetUuid}", method = RequestMethod.DELETE)
    public void deleteTweet(@PathVariable String tweetUuid) {
        Tweet tweetToDelete = tweetRepository.findByUuid(tweetUuid);
        if (tweetToDelete != null) {
            tweetRepository.delete(tweetToDelete);
        }
    }

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
