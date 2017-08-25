package com.imangazalievm.bubbble.domain.models;

public class ShotsRequestParams {

    private String sort;
    private int page;
    private int pageSize;

    public ShotsRequestParams(String sort, int page, int pageSize) {
        this.sort = sort;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

}
