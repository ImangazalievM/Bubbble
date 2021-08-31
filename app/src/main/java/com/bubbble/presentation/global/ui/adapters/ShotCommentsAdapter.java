package com.bubbble.presentation.global.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bubbble.R;
import com.bubbble.domain.global.models.Comment;
import com.bubbble.presentation.global.ui.adapters.viewholders.CommentViewHolder;
import com.bubbble.presentation.global.ui.adapters.viewholders.NoCommentsViewHolder;
import com.bubbble.presentation.global.ui.adapters.viewholders.ShotDescriptionViewHolder;
import com.bubbble.presentation.global.ui.commons.glide.GlideCircleTransform;


public class ShotCommentsAdapter extends LoadMoreAdapter<Comment> {

    public interface OnLinkClickListener {
        void onLinkClicked(String url);
    }

    public interface OnUserSelectedListener {
        void onUserSelected(long userId);
    }

    private Context context;
    private LayoutInflater layoutInflater;
    private final View description;
    private boolean noComments;

    private OnLinkClickListener onLinkClickListener;
    private OnUserSelectedListener onUserSelectedListener;

    public ShotCommentsAdapter(Context context, View description) {
        super(true, true);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.description = description;
        this.noComments = false;
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.onLinkClickListener = onLinkClickListener;
    }

    public void setOnUserSelectedListener(OnUserSelectedListener onUserSelectedListener) {
        this.onUserSelectedListener = onUserSelectedListener;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new CommentViewHolder(layoutInflater.inflate(R.layout.item_shot_comment, parent, false));

    }

    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ShotDescriptionViewHolder(description);
    }

    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return noComments ? new NoCommentsViewHolder(layoutInflater, parent) : super.getFooterViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            Comment comment = getItem(position);
            commentViewHolder.userName.setText(comment.getUser().getName());
            commentViewHolder.commentText.setHtmlText(comment.getBody());
            commentViewHolder.likesCount.setText(String.valueOf(comment.getLikeCount()));
            Glide.with(context)
                    .load(comment.getUser().getAvatarUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .transform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(commentViewHolder.userAvatar);

            commentViewHolder.userAvatar.setOnClickListener(v -> onUserSelected(comment.getUser().getId()));
            commentViewHolder.commentText.setOnLinkClickListener(this::onLinkClicked);
            commentViewHolder.commentText.setOnUserSelectedListener(this::onUserSelected);
        }
    }

    private void onLinkClicked(String url) {
        if (onLinkClickListener != null) {
            onLinkClickListener.onLinkClicked(url);
        }
    }

    private void onUserSelected(long userId) {
        if (onUserSelectedListener != null) {
            onUserSelectedListener.onUserSelected(userId);
        }
    }

    public void showLoadingIndicator() {
        showFooter(true);
    }

    public void hideLoadingIndicator() {
        showFooter(false);
    }

    public void setNoComments() {
        this.noComments = true;
        showFooter(true);
    }

}