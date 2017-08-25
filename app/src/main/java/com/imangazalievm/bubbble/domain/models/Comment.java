package com.imangazalievm.bubbble.domain.models;

import java.util.Date;

public class Comment {

    private long id;
    private User user;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private String likesUrl;
    private int likesCount;

    // todo move this into a decorator
    private boolean liked;
    private String commentBody;

    public Comment(long id, User user, String body, Date createdAt, Date updatedAt, String likesUrl, int likesCount) {
        this.id = id;
        this.user = user;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesUrl = likesUrl;
        this.likesCount = likesCount;
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
