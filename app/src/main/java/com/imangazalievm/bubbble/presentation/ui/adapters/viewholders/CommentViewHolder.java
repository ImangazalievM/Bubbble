package com.imangazalievm.bubbble.presentation.ui.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.presentation.ui.views.dribbbletextview.DribbbleTextView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public ImageView userAvatar;
    public DribbbleTextView commentText;
    public TextView likesCount;

    public CommentViewHolder(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.user_name);
        userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        commentText = (DribbbleTextView) itemView.findViewById(R.id.text);
        likesCount = (TextView) itemView.findViewById(R.id.likes_count);
    }

}