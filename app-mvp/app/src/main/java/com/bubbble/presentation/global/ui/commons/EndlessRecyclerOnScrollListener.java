package com.bubbble.presentation.global.ui.commons;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int DEFAULT_VISIBLE_THRESHOLD = 5;

    private int visibleThreshold = DEFAULT_VISIBLE_THRESHOLD;
    private int previousTotal = 0;
    private boolean loading = true;

    private int currentPage = 1;

    private LinearLayoutManager linearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();

}