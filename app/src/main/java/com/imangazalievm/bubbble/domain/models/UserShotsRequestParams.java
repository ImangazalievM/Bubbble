package com.imangazalievm.bubbble.domain.models;

public class UserShotsRequestParams {

    private long userId;
    private int page;
    private int pageSize;

    public UserShotsRequestParams(long userId, int page, int pageSize) {
        this.userId = userId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public long getUserId() {
        return userId;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

}
