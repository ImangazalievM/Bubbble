package com.bubbble.coreui.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bubbble.core.models.user.Follow;
import com.bubbble.core.models.user.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bubbble.R;
import com.bubbble.coreui.ui.commons.glide.GlideCircleTransform;

public class UserFollowersAdapter extends LoadMoreAdapter<Follow> {

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder instanceof FollowerViewHolder) {
            FollowerViewHolder followerViewHolder = (FollowerViewHolder) holder;
            Follow follow = getItem(position);
            User user = follow.getFollower();
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

        final View userLayout;
        final ImageView userAvatar;
        final TextView userName;
        final TextView userLocation;
        final TextView userShotsCount;
        final TextView userShotsLabel;
        final TextView userFollowersCount;
        final TextView userFollowersLabel;

        FollowerViewHolder(View itemView) {
            super(itemView);
            userLayout = itemView.findViewById(R.id.user_container);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
            userLocation = itemView.findViewById(R.id.user_location);
            userShotsCount = itemView.findViewById(R.id.user_shots_count);
            userShotsLabel = itemView.findViewById(R.id.user_shots_label);
            userFollowersCount = itemView.findViewById(R.id.user_followers_count);
            userFollowersLabel = itemView.findViewById(R.id.user_followers_label);
        }

    }

}