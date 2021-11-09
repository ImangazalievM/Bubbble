package com.bubbble.shots.shotslist

import android.content.Context
import android.util.Log
import com.bubbble.coreui.ui.adapters.LoadMoreAdapter
import com.bubbble.core.models.shot.Shot
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.shots.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bubbble.coreui.ui.views.badgedimageview.BadgedImageView

class ShotsAdapter(
    private val context: Context
) : LoadMoreAdapter<Shot?>(false, false) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Int) -> Unit)?) {
        this.onItemClickListener = listener
    }

    override fun getItemViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return ShotViewHolder(layoutInflater.inflate(R.layout.item_shot, parent, false))
    }

    override fun getHeaderViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ShotViewHolder) {
            val shot = getItem(position)!!
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

            holder.shotLayout.setOnClickListener {
                onItemClickListener?.invoke(position)
            }
            if (shot.isAnimated) {
                holder.shotImage.setBadge("GIF")
            }
        }
    }

    private inner class ShotViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var shotLayout: View = itemView.findViewById(R.id.shot_layout)
        var shotImage: BadgedImageView = itemView.findViewById(R.id.shot_image)
    }

}