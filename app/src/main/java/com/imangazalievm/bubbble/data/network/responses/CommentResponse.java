package com.imangazalievm.bubbble.data.network.responses;

import java.util.Date;

public class CommentResponse {

    private long id;
    private UserResponse user;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private String likesUrl;
    private int likesCount;

    public long getId() {
        return id;
    }

    public UserResponse getUser() {
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
