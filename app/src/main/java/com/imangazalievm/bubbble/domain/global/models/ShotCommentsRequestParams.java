package com.imangazalievm.bubbble.domain.global.models;

public class ShotCommentsRequestParams {

    private long shotId;
    private int page;
    private int pageSize;

    public ShotCommentsRequestParams(long shotId, int page, int pageSize) {
        this.shotId = shotId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public long getShotId() {
        return shotId;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

}
