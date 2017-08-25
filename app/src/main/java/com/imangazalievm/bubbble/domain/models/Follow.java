package com.imangazalievm.bubbble.domain.models;

import java.util.Date;

public class Follow {

    private long id;
    private Date createdAt;
    private User user;

    public Follow(long id, Date createdAt, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Date getDateCreated() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

}
