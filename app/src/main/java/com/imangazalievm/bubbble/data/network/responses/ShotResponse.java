package com.imangazalievm.bubbble.data.network.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShotResponse {

    private long id;
    private String title;
    private String description;
    private int width;
    private int height;
    private ImagesResponse images;
    private int viewsCount;
    private int likesCount;
    private int bucketsCount;
    private int commentsCount;
    private Date createdAt;
    private Date updatedAt;
    private String htmlUrl;
    private String reboundSourceUrl;
    private List<String> tags = new ArrayList<>();
    private UserResponse user;
    private TeamResponse team;
    private boolean animated;

    public ShotResponse() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImagesResponse getImages() {
        return images;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getReboundSourceUrl() {
        return reboundSourceUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public UserResponse getUser() {
        return user;
    }

    public TeamResponse getTeam() {
        return team;
    }

    public boolean isAnimated() {
        return animated;
    }
}