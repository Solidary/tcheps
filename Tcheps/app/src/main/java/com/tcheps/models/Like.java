package com.tcheps.models;

import java.util.Date;

/**
 * Created by maelfosso on 10/8/15.
 */
public class Like {
    private User author;
    private Date liked;

    public Like() {

    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getLiked() {
        return liked;
    }

    public void setLiked(Date liked) {
        this.liked = liked;
    }
}
