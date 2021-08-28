package com.imangazalievm.bubbble.presentation.global.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imangazalievm.bubbble.presentation.global.ui.adapters.viewholders.LoadMoreViewHolder;


public abstract class LoadMoreAdapter<T> extends HeaderFooterAdapter<T> {

    public interface OnRetryLoadMoreListener {
        void onRetryLoadMore();
    }

    private OnRetryLoadMoreListener onRetryLoadMoreListener;
    private boolean loadingError = false;

    public LoadMoreAdapter(boolean withHeader, boolean withFooter) {
        super(withHeader, withFooter);
    }

    public void setOnRetryLoadMoreListener(OnRetryLoadMoreListener onRetryLoadMoreListener) {
        this.onRetryLoadMoreListener = onRetryLoadMoreListener;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new LoadMoreViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.retryButton.setOnClickListener(v -> onRetryLoadMoreClick());
            loadMoreViewHolder.showError(loadingError);
        }
    }

    private void onRetryLoadMoreClick() {
        if (onRetryLoadMoreListener != null) {
            onRetryLoadMoreListener.onRetryLoadMore();
        }
    }

    public void setLoadingMore(boolean isLoading) {
        this.loadingError = !isLoading;
        showFooter(isLoading);
    }

    public void setLoadingError(boolean loadingError) {
        this.loadingError = loadingError;
        showFooter(loadingError);
    }

}