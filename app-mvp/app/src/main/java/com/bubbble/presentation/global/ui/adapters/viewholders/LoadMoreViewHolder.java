package com.bubbble.presentation.global.ui.adapters.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.bubbble.R;

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    public final ProgressBar loadMoreProgressBar;
    public final ViewGroup loadMoreErrorLayout;
    public final Button retryButton;

    public LoadMoreViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.item_load_more, parent, false));

        loadMoreProgressBar = itemView.findViewById(R.id.load_more_comments_progress);
        loadMoreErrorLayout = itemView.findViewById(R.id.load_more_error_layout);
        retryButton = itemView.findViewById(R.id.retry_button);
    }

    public void showError(boolean loadingError) {
        loadMoreProgressBar.setVisibility(loadingError ? View.GONE : View.VISIBLE);
        loadMoreErrorLayout.setVisibility(loadingError ? View.VISIBLE : View.GONE);
    }

}