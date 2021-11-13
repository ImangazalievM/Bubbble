package com.bubbble.shots.shotslist

import androidx.recyclerview.widget.DiffUtil
import com.bubbble.core.models.shot.Shot

object ShotComparator : DiffUtil.ItemCallback<Shot>() {
  override fun areItemsTheSame(oldItem: Shot, newItem: Shot): Boolean {
    // Id is unique.
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Shot, newItem: Shot): Boolean {
    return oldItem == newItem
  }
}