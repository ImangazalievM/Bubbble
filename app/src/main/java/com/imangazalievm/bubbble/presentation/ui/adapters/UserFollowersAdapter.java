package com.imangazalievm.bubbble.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.ui.adapters.viewholders.LoadMoreViewHolder;
import com.imangazalievm.bubbble.presentation.ui.commons.glide.GlideCircleTransform;


public class UserFollowersAdapter extends HeaderFooterAdapter<Follow> {

    public interface OnItemClickListener {
        void onFollowerItemClick(int position);
    }

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public UserFollowersAdapter(Context context) {
        super(false, true);

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new FollowerViewHolder(layoutInflater.inflate(R.layout.item_follower, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new LoadMoreViewHolder(layoutInflater.inflate(R.layout.item_load_more, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FollowerViewHolder) {
            FollowerViewHolder followerViewHolder = (FollowerViewHolder) holder;
            Follow follow = getItem(position);
            User user = follow.getUser();
            Glide.with(context)
                    .load(user.getAvatarUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .transform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(followerViewHolder.userAvatar);
            followerViewHolder.userName.setText(user.getUsername());
            followerViewHolder.userLocation.setText(user.getLocation());
            followerViewHolder.userShotsCount.setText(String.valueOf(user.getShotsCount()));
            followerViewHolder.userShotsLabel.setText(context.getResources().getQuantityString(R.plurals.shots,
                    user.getShotsCount(), ""));
            followerViewHolder.userFollowersCount.setText(String.valueOf(user.getFollowersCount()));
            followerViewHolder.userFollowersLabel.setText(context.getResources().getQuantityString(R.plurals.followers,
                    user.getFollowersCount(), ""));


            followerViewHolder.userLayout.setOnClickListener(v -> onShotItemClick(position));
        }
    }

    private void onShotItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onFollowerItemClick(position);
        }
    }

    public void showLoadingIndicator() {
        showFooter(true);
    }

    public void hideLoadingIndicator() {
        showFooter(false);
    }

    private class FollowerViewHolder extends RecyclerView.ViewHolder {

        View userLayout;
        ImageView userAvatar;
        TextView userName;
        TextView userLocation;
        TextView userShotsCount;
        TextView userShotsLabel;
        TextView userFollowersCount;
        TextView userFollowersLabel;

        FollowerViewHolder(View itemView) {
            super(itemView);
            userLayout = itemView.findViewById(R.id.user_container);
            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userLocation = (TextView) itemView.findViewById(R.id.user_location);
            userShotsCount = (TextView) itemView.findViewById(R.id.user_shots_count);
            userShotsLabel = (TextView) itemView.findViewById(R.id.user_shots_label);
            userFollowersCount = (TextView) itemView.findViewById(R.id.user_followers_count);
            userFollowersLabel = (TextView) itemView.findViewById(R.id.user_followers_label);
        }

    }

}