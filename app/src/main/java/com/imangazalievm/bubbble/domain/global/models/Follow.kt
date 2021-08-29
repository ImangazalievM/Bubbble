package com.imangazalievm.bubbble.domain.global.models;

import java.util.Date;

public class Follow {

    private long id;
    private Date createdAt;
    private User follower;

    public long getId() {
        return id;
    }

    public Date getDateCreated() {
        return createdAt;
    }

    public User getFollower() {
        return follower;
    }

}
