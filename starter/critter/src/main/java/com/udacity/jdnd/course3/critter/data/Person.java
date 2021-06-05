package com.udacity.jdnd.course3.critter.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Person {
    @Id
    @GeneratedValue
    Long id;

    String name;
}
