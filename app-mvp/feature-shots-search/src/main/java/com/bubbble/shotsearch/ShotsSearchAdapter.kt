package com.bubbble.shotsearch

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.ui.extensions.inflate
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.views.badgedimageview.BadgedImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ShotsSearchAdapter(
    diffCallback: DiffUtil.ItemCallback<Shot>,
    private val onItemClickListener: (Shot) -> Unit
) : PagingDataAdapter<Shot, ShotsSearchAdapter.ShotViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShotViewHolder {
        return ShotViewHolder(parent.inflate(R.layout.item_search_shot, false))
    }

    override fun onBindViewHolder(holder: ShotViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        val shot = getItem(position)!!
        val context = holder.itemView.context
        Glide.with(context)
            .load(shot.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .centerCrop()
            .crossFade()
            .into(holder.shotImage)

        val backgroundResId = when {
            position % 3 == 0 -> R.color.grey_dark_100
            position % 2 == 0 -> R.color.grey_dark_200
            else -> R.color.grey_dark_300
        }

        holder.shotLayout.setBackgroundColor(
            ContextCompat.getColor(context, backgroundResId)
        )

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(shot)
        }
        if (shot.isAnimated) {
            holder.shotImage.setBadge("GIF")
        }
    }

    inner class ShotViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var shotLayout: View = itemView.findViewById(R.id.shot_layout)
        var shotImage: BadgedImageView = itemView.findViewById(R.id.shot_image)
    }

}