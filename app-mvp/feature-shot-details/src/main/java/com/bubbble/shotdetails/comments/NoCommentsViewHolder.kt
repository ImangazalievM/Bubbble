package com.bubbble.shotdetails.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.shotdetails.R

internal class NoCommentsViewHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_no_comments, parent, false))