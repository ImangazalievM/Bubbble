package com.imangazalievm.bubbble.data.network.responses;

import java.util.Date;

public class UserResponse {

    private long id;
    private String name;
    private String username;
    private String htmlUrl;
    private String avatarUrl;
    private String bio;
    private String location;
    public LinksResponse links;
    private int bucketsCount;
    private int commentsReceivedCount;
    private int followersCount;
    private int followingsCount;
    private int likesCount;
    private int likesReceivedCount;
    private int projectsCount;
    private int reboundsReceivedCount;
    private int shotsCount;
    private int teamsCount;
    private boolean canUploadShot;
    private String type;
    private boolean pro;
    private String bucketsUrl;
    private String followersUrl;
    private String followingUrl;
    private String likesUrl;
    private String shotsUrl;
    private String teamsUrl;
    private Date createdAt;
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public LinksResponse getLinks() {
        return links;
    }

    public String getLocation() {
        return location;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public int getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getLikesReceivedCount() {
        return likesReceivedCount;
    }

    public int getProjectsCount() {
        return projectsCount;
    }

    public int getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public int getTeamsCount() {
        return teamsCount;
    }

    public boolean getCanUploadShot() {
        return canUploadShot;
    }

    public String getType() {
        return type;
    }

    public boolean isPro() {
        return pro;
    }

    public String getBucketsUrl() {
        return bucketsUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public String getLikesUrl() {
        return likesUrl;
    }

    public String getShotsUrl() {
        return shotsUrl;
    }

    public String getTeamsUrl() {
        return teamsUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

}
