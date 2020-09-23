package com.imangazalievm.bubbble.presentation.ui.global.commons;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;

    public DividerItemDecoration(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, new int [] { android.R.attr.listDivider });
        divider = a.getDrawable(0);
        a.recycle();
    }

    public DividerItemDecoration(Drawable divider) { this.divider = divider; }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (divider == null) return;
        if (parent.getChildPosition(view) < 1) return;

        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) outRect.top = divider.getIntrinsicHeight();
        else outRect.left = divider.getIntrinsicWidth();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        if (divider == null) { super.onDrawOver(c, parent); return; }

        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();

            for (int i=1; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int size = divider.getIntrinsicHeight();
                final int top = child.getTop() - params.topMargin;
                final int bottom = top + size;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        } else { //horizontal
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();

            for (int i=1; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int size = divider.getIntrinsicWidth();
                final int left = child.getLeft() - params.leftMargin;
                final int right = left + size;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else throw new IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager.");
    }

}