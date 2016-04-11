package com.jglitter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Encapsulates a database record having a primary key.
 */
@MappedSuperclass
public abstract class HasPrimaryKey {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    public Integer getId() {
        return id;
    }
}
