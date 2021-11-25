package com.bubbble.presentation.userprofile

import android.content.Context
import com.bubbble.coreui.ui.adapters.LoadMoreAdapter
import com.bubbble.core.models.user.Follow
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bubbble.coreui.ui.commons.glide.GlideCircleTransform
import com.bubbble.userprofile.databinding.ItemFollowerBinding

class UserFollowersAdapter(
    private val context: Context
) : LoadMoreAdapter<Follow>(false, true) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var onItemClickListener: ((Follow) -> Unit)? = null

    override fun getItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = ItemFollowerBinding.inflate(layoutInflater)
        return FollowerViewHolder(binding)
    }

    override fun getHeaderViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder !is FollowerViewHolder) return

        val follow = getItem(position)
        val user = follow.follower
        Glide.with(context)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(GlideCircleTransform(context))
            .crossFade()
            .into(holder.binding.userAvatar)

        with(holder.binding) {
            userName.text = user.userName
            userLocation.text = user.location
            userShotsCount.text = user.shotsCount.toString()
            userShotsLabel.text = context.resources.getQuantityString(
                R.plurals.shots,
                user.shotsCount, ""
            )
            userFollowersCount.text = user.followersCount.toString()
            userFollowersLabel.text = context.resources.getQuantityString(
                R.plurals.followers,
                user.followersCount, ""
            )
            userContainer.setOnClickListener {
                onItemClickListener?.invoke(follow)
            }
        }
    }

    fun showLoadingIndicator() {
        showFooter(true)
    }

    fun hideLoadingIndicator() {
        showFooter(false)
    }

    private inner class FollowerViewHolder constructor(
        val binding: ItemFollowerBinding
    ) : RecyclerView.ViewHolder(binding.root)
}