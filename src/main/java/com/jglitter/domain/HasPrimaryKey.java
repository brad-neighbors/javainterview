package com.jglitter.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class HasPrimaryKey {

    @Id
    @GeneratedValue
    @XmlTransient
    private Integer id;

    @XmlTransient
    public Integer getId() {
        return id;
    }
}
