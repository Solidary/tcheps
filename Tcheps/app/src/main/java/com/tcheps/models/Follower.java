package com.tcheps.models;

import java.util.Date;

/**
 * Created by maelfosso on 0/8/15.
 */
public class Follower {

    private User author;
    private Date followed;

    public Follower() {

    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getFollowed() {
        return followed;
    }

    public void setFollowed(Date followed) {
        this.followed = followed;
    }
}
