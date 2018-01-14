package com.imangazalievm.bubbble.domain.global.models;

import java.util.Date;

public class User {

    private long id;
    private String name;
    private String username;
    private String htmlUrl;
    private String avatarUrl;
    private String bio;
    private String location;
    private Links links;
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

    public User() {
    }

    public User(long id,
                String name,
                String username,
                String htmlUrl,
                String avatarUrl,
                String bio,
                String location,
                Links links,
                int bucketsCount,
                int commentsReceivedCount,
                int followersCount,
                int followingsCount,
                int likesCount,
                int likesReceivedCount,
                int projectsCount,
                int reboundsReceivedCount,
                int shotsCount,
                int teamsCount,
                boolean canUploadShot,
                String type,
                boolean pro,
                String bucketsUrl,
                String followersUrl,
                String followingUrl,
                String likesUrl,
                String shotsUrl,
                String teamsUrl,
                Date createdAt,
                Date updatedAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.htmlUrl = htmlUrl;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.location = location;
        this.links = links;
        this.bucketsCount = bucketsCount;
        this.commentsReceivedCount = commentsReceivedCount;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
        this.likesCount = likesCount;
        this.likesReceivedCount = likesReceivedCount;
        this.projectsCount = projectsCount;
        this.reboundsReceivedCount = reboundsReceivedCount;
        this.shotsCount = shotsCount;
        this.teamsCount = teamsCount;
        this.canUploadShot = canUploadShot;
        this.type = type;
        this.pro = pro;
        this.bucketsUrl = bucketsUrl;
        this.followersUrl = followersUrl;
        this.followingUrl = followingUrl;
        this.likesUrl = likesUrl;
        this.shotsUrl = shotsUrl;
        this.teamsUrl = teamsUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public Links getLinks() {
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

    public static class Builder {
        
        private long id;
        private String name;
        private String username;
        private String htmlUrl;
        private String avatarUrl;
        private String bio;
        private String location;
        private Links links;
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

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public Builder setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setLinks(Links links) {
            this.links = links;
            return this;
        }

        public Builder setBucketsCount(int bucketsCount) {
            this.bucketsCount = bucketsCount;
            return this;
        }

        public Builder setCommentsReceivedCount(int commentsReceivedCount) {
            this.commentsReceivedCount = commentsReceivedCount;
            return this;
        }

        public Builder setFollowersCount(int followersCount) {
            this.followersCount = followersCount;
            return this;
        }

        public Builder setFollowingsCount(int followingsCount) {
            this.followingsCount = followingsCount;
            return this;
        }

        public Builder setLikesCount(int likesCount) {
            this.likesCount = likesCount;
            return this;
        }

        public Builder setLikesReceivedCount(int likesReceivedCount) {
            this.likesReceivedCount = likesReceivedCount;
            return this;
        }

        public Builder setProjectsCount(int projectsCount) {
            this.projectsCount = projectsCount;
            return this;
        }

        public Builder setReboundsReceivedCount(int reboundsReceivedCount) {
            this.reboundsReceivedCount = reboundsReceivedCount;
            return this;
        }

        public Builder setShotsCount(int shotsCount) {
            this.shotsCount = shotsCount;
            return this;
        }

        public Builder setTeamsCount(int teamsCount) {
            this.teamsCount = teamsCount;
            return this;
        }

        public Builder setCanUploadShot(boolean canUploadShot) {
            this.canUploadShot = canUploadShot;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPro(boolean pro) {
            this.pro = pro;
            return this;
        }

        public Builder setBucketsUrl(String bucketsUrl) {
            this.bucketsUrl = bucketsUrl;
            return this;
        }

        public Builder setFollowersUrl(String followersUrl) {
            this.followersUrl = followersUrl;
            return this;
        }

        public Builder setFollowingUrl(String followingUrl) {
            this.followingUrl = followingUrl;
            return this;
        }

        public Builder setLikesUrl(String likesUrl) {
            this.likesUrl = likesUrl;
            return this;
        }

        public Builder setShotsUrl(String shotsUrl) {
            this.shotsUrl = shotsUrl;
            return this;
        }

        public Builder setTeamsUrl(String teamsUrl) {
            this.teamsUrl = teamsUrl;
            return this;
        }

        public Builder setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            return new User(id, name, username, htmlUrl, avatarUrl, bio, location, links, bucketsCount, commentsReceivedCount, followersCount, followingsCount, likesCount, likesReceivedCount, projectsCount, reboundsReceivedCount, shotsCount, teamsCount, canUploadShot, type, pro, bucketsUrl, followersUrl, followingUrl, likesUrl, shotsUrl, teamsUrl, createdAt, updatedAt);
        }
    }

}
