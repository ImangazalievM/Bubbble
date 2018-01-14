package com.imangazalievm.bubbble.domain.global.models;

public class ShotsSearchRequestParams {

    private String searchQuery;
    private String sort;
    private int page;
    private int pageSize;

    public ShotsSearchRequestParams(String searchQuery, String sort, int page, int pageSize) {
        this.searchQuery = searchQuery;
        this.sort = sort;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getSearchQuery() {
        return searchQuery;
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
