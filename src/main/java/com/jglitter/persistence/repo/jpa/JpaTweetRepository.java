/*
 * Project Horizon
 *
 * (c) 2012 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */
package com.jglitter.persistence.repo.jpa;

import com.jglitter.domain.Tweet;
import com.jglitter.domain.User;
import com.jglitter.persistence.repo.TweetRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class JpaTweetRepository implements TweetRepository {

    @PersistenceContext
    private EntityManager em;

    public Tweet persist(final Tweet tweet) {
        em.persist(tweet);
        return tweet;
    }

    public Tweet findById(final Integer id) {
        return em.getReference(Tweet.class, id);
    }

    public Collection<Tweet> findAllByAuthor(final User author) {
        final TypedQuery<Tweet> query = em.createQuery("select tw from Tweet tw where tw.author = :auth", Tweet.class);
        return query.setParameter("auth", author).getResultList();
    }

    @Override
    public void delete(Tweet tweet) {
        em.remove(tweet);
    }

    @Override
    public Tweet findByUuid(String tweetUuid) {
        final TypedQuery<Tweet> query = em.createQuery("select tw from Tweet tw where tw.uuid = :uuid", Tweet.class);
        return query.setParameter("uuid", tweetUuid).getSingleResult();
    }
}
