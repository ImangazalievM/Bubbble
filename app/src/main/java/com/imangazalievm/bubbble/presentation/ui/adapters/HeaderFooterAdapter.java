package com.imangazalievm.bubbble.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class HeaderFooterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<T> data;

    private boolean withHeader;
    private boolean withFooter;

    public HeaderFooterAdapter(boolean withHeader, boolean withFooter) {
        this.data = new ArrayList<>();
        this.withHeader = withHeader;
        this.withFooter = withFooter;
    }

    public void showHeader(boolean showHeader) {
        this.withHeader = showHeader;
        notifyDataSetChanged();
    }

    public void showFooter(boolean showFooter) {
        this.withFooter = showFooter;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    //region Get View
    protected abstract RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getHeaderViewHolder(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemViewHolder(inflater, parent);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderViewHolder(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterViewHolder(inflater, parent);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemCount() {
        int itemCount = data.size();
        if (withHeader)
            itemCount++;
        if (withFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (withHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (withFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected T getItem(int position) {
        return withHeader ? data.get(position - 1) : data.get(position);
    }

}