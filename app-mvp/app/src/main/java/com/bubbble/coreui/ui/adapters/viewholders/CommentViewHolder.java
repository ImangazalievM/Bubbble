package com.bubbble.coreui.ui.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bubbble.R;
import com.bubbble.coreui.ui.views.dribbbletextview.DribbbleTextView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public final TextView userName;
    public final ImageView userAvatar;
    public final DribbbleTextView commentText;
    public final TextView likesCount;

    public CommentViewHolder(View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.user_name);
        userAvatar = itemView.findViewById(R.id.user_avatar);
        commentText = itemView.findViewById(R.id.text);
        likesCount = itemView.findViewById(R.id.likes_count);
    }

}