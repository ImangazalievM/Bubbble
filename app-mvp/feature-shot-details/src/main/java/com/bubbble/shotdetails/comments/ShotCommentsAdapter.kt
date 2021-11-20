package com.bubbble.shotdetails.comments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.core.models.Comment
import com.bubbble.coreui.ui.adapters.LoadMoreAdapter
import com.bubbble.coreui.ui.commons.glide.GlideCircleTransform
import com.bubbble.shotdetails.R
import com.bubbble.shotdetails.ShotDescriptionViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

internal class ShotCommentsAdapter(
    private val context: Context,
    private val onLinkClick: (url: String) -> Unit,
    private val onUserUrlClick: (url: String) -> Unit,
    private val onUserClick: (userName: String) -> Unit,
    private val description: View
) : LoadMoreAdapter<Comment?>(true, true) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var noComments: Boolean = false

    override fun getItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return CommentViewHolder(layoutInflater.inflate(R.layout.item_shot_comment, parent, false))
    }

    override fun getHeaderViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return ShotDescriptionViewHolder(description)
    }

    override fun getFooterViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return if (noComments) NoCommentsViewHolder(
            layoutInflater,
            parent
        ) else super.getFooterViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is CommentViewHolder) {
            val comment = getItem(position)
            holder.userName.text = comment!!.user.name
            holder.commentText.setHtmlText(comment.body)
            holder.likesCount.text = comment.likeCount.toString()
            Glide.with(context)
                .load(comment.user.avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(GlideCircleTransform(context))
                .crossFade()
                .into(holder.userAvatar)
            holder.userAvatar.setOnClickListener {
                onUserClick(comment.user.userName)
            }
            holder.commentText.setOnLinkClickListener { url: String -> onLinkClick(url) }
            holder.commentText.setOnUserSelectedListener { url: String ->
                onUserUrlClick(url)
            }
        }
    }

    fun showLoadingIndicator() {
        showFooter(true)
    }

    fun hideLoadingIndicator() {
        showFooter(false)
    }

    fun setNoComments() {
        noComments = true
        showFooter(true)
    }

}