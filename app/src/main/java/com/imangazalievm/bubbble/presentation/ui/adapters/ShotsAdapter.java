package com.imangazalievm.bubbble.presentation.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.presentation.ui.adapters.viewholders.LoadMoreViewHolder;
import com.imangazalievm.bubbble.presentation.ui.views.badgedimageview.BadgedImageView;

import java.util.ArrayList;
import java.util.List;


public class ShotsAdapter extends LoadMoreAdapter<Shot> {

    public interface OnItemClickListener {
        void onShotItemClick(int position);
    }

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public ShotsAdapter(Context context) {
        super(false, false);

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ShotViewHolder(layoutInflater.inflate(R.layout.item_shot, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder instanceof ShotViewHolder) {
            ShotViewHolder shotViewHolder = (ShotViewHolder) holder;
            Shot shot = getItem(position);
            Glide.with(context)
                    .load(shot.getImages().best())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .crossFade()
                    .into(shotViewHolder.shotImage);

            if (position % 3 == 0) {
                shotViewHolder.shotLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark_100));
            } else if (position % 2 == 0) {
                shotViewHolder.shotLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark_200));
            } else {
                shotViewHolder.shotLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_dark_300));
            }
            shotViewHolder.shotLayout.setOnClickListener(v -> onShotItemClick(position));

            if (shot.isAnimated()) {
                shotViewHolder.shotImage.setBadge("GIF");
            }
        }
    }

    private void onShotItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onShotItemClick(position);
        }
    }

    private class ShotViewHolder extends RecyclerView.ViewHolder {

        View shotLayout;
        BadgedImageView shotImage;

        ShotViewHolder(View itemView) {
            super(itemView);
            shotLayout = itemView.findViewById(R.id.shot_layout);
            shotImage = itemView.findViewById(R.id.shot_image);
        }

    }

}