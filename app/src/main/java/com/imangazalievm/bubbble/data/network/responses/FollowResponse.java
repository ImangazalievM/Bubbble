package com.imangazalievm.bubbble.data.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FollowResponse {

    private long id;
    private Date createdAt;
    @SerializedName("follower")
    private UserResponse user;

    public long getId() {
        return id;
    }

    public Date getDateCreated() {
        return createdAt;
    }

    public UserResponse getUser() {
        return user;
    }

}
