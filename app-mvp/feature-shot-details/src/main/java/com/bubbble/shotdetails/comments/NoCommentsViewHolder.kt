package com.bubbble.shotdetails.comments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bubbble.shotdetails.R;

public class NoCommentsViewHolder extends RecyclerView.ViewHolder {

    public NoCommentsViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.item_no_comments, parent, false));
    }

}