package com.dragonjiang.databindingdemo.model;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class User {
    public final String firstName;
    public final String lastName;
    public boolean isAdult;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    @Override
    public String toString() {
        return firstName.toUpperCase() + " " + lastName.toUpperCase();
    }
}
