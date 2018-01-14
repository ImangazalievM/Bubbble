package com.imangazalievm.bubbble.domain.global.models;

import java.util.Date;

public class Comment {

    private long id;
    private User user;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private String likesUrl;
    private int likesCount;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getLikesUrl() {
        return likesUrl;
    }

    public int getLikesCount() {
        return likesCount;
    }
}
