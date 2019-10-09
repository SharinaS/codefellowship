package com.sharinas.codefellowship.models;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    ApplicationUser owner;

    private final String body;
    private final String timeStamp;

    public Post(String body, String timeStamp, ApplicationUser owner) {
        this.body = body;
        this.timeStamp = timeStamp;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public ApplicationUser getOwner() {
        return owner;
    }

    public String getBody() {
        return body;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String toString() {
        return String.format("% wrote this post at %: %., this.owner, this.body, this.timestamp");
    }

}
