package com.bubbble.shotdetails.comments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.coreui.ui.views.dribbbletextview.DribbbleTextView
import com.bubbble.shotdetails.R

internal class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userName: TextView = itemView.findViewById(R.id.user_name)
    val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
    val commentText: DribbbleTextView = itemView.findViewById(R.id.text)
    val likesCount: TextView = itemView.findViewById(R.id.likes_count)

}