package com.imangazalievm.bubbble.domain.models;

import java.util.Date;
import java.util.List;

public class Shot {

    private long id;
    private String title;
    private String description;
    private int width;
    private int height;
    private Images images;
    private int viewsCount;
    private int likesCount;
    private int bucketsCount;
    private int commentsCount;
    private Date createdAt;
    private Date updatedAt;
    private String htmlUrl;
    private String reboundSourceUrl;
    private List<String> tags;
    private User user;
    private Team team;
    private boolean animated;

    public Shot() {
    }

    public Shot(long id,
                String title,
                String description,
                int width,
                int height,
                Images images,
                int viewsCount,
                int likesCount,
                int bucketsCount,
                int commentsCount,
                Date createdAt,
                Date updatedAt,
                String htmlUrl,
                String reboundSourceUrl,
                List<String> tags,
                User user,
                Team team,
                boolean animated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.width = width;
        this.height = height;
        this.images = images;
        this.viewsCount = viewsCount;
        this.likesCount = likesCount;
        this.bucketsCount = bucketsCount;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.htmlUrl = htmlUrl;
        this.reboundSourceUrl = reboundSourceUrl;
        this.tags = tags;
        this.user = user;
        this.team = team;
        this.animated = animated;
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

    public Images getImages() {
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

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isAnimated() {
        return animated;
    }

    public static class Builder {

        private long id;
        private String title;
        private String description;
        private int width;
        private int height;
        private Images images;
        private int viewsCount;
        private int likesCount;
        private int bucketsCount;
        private int commentsCount;
        private Date createdAt;
        private Date updatedAt;
        private String htmlUrl;
        private String reboundSourceUrl;
        private List<String> tags;
        private User user;
        private Team team;
        private boolean animated;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setImages(Images images) {
            this.images = images;
            return this;
        }

        public Builder setViewsCount(int viewsCount) {
            this.viewsCount = viewsCount;
            return this;
        }

        public Builder setLikesCount(int likesCount) {
            this.likesCount = likesCount;
            return this;
        }

        public Builder setBucketsCount(int bucketsCount) {
            this.bucketsCount = bucketsCount;
            return this;
        }

        public Builder setCommentsCount(int commentsCount) {
            this.commentsCount = commentsCount;
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

        public Builder setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public Builder setReboundSourceUrl(String reboundSourceUrl) {
            this.reboundSourceUrl = reboundSourceUrl;
            return this;
        }

        public Builder setTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTeam(Team team) {
            this.team = team;
            return this;
        }

        public Builder setAnimated(boolean animated) {
            this.animated = animated;
            return this;
        }

        public Shot build() {
            return new Shot(id, title, description, width, height, images, viewsCount, likesCount, bucketsCount, commentsCount, createdAt, updatedAt, htmlUrl, reboundSourceUrl, tags, user, team, animated);
        }

    }

}
