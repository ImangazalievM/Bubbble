package com.imangazalievm.bubbble.presentation.global.ui.views.badgedimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.imangazalievm.bubbble.R;

/**
 * ImageView implementation that allows you to place 'badges' over the top that describe the image
 */
public class BadgedImageView extends ImageView {

    private boolean badgeBoundsSet = false;

    private BadgeDrawable badge;
    private int badgeGravity;
    private int badgePadding;
    private int badgePaddingX;
    private int badgePaddingY;
    private int badgeColor;

    /**
     * Create a new BadgedImageView. Use 0 padding, default gravity, and white for the color
     * @param context current activity
     */
    public BadgedImageView(Context context) {
        this(context, 0, 0, Color.WHITE);
    }

    /**
     * Create a new BadgedImageView
     * @param context current activity
     * @param badgeGravity Gravity.TOP, Gravity.BOTTOM, Gravity.RIGHT, Gravity.LEFT, Gravity.START, Gravity.END
     * @param badgePadding Padding in DP
     * @param badgeColor color you want the badge to be
     */
    public BadgedImageView(Context context, int badgeGravity, int badgePadding, int badgeColor) {
        super(context);

        this.badgeGravity = badgeGravity;
        this.badgePadding = badgePadding;
        this.badgeColor = badgeColor;
    }

    /**
     * Constructor for inflation from XML layout
     * @param context current activity
     * @param attrs provided by layout
     */
    public BadgedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgedImageView, 0, 0);
        badgeGravity = a.getInt(R.styleable.BadgedImageView_bubbble_badgeGravity, Gravity.START | Gravity.BOTTOM);
        badgeColor = a.getColor(R.styleable.BadgedImageView_badgeColor, Color.WHITE);
        badgePadding = a.getDimensionPixelSize(R.styleable.BadgedImageView_badgePadding, 0);
        badgePaddingX = a.getDimensionPixelSize(R.styleable.BadgedImageView_badgePaddingX, 0);
        badgePaddingY = a.getDimensionPixelSize(R.styleable.BadgedImageView_badgePaddingY, 0);
        String badgeText = a.getString(R.styleable.BadgedImageView_badgeText);
        a.recycle();

        if (badgeText != null) {
            setBadge(badgeText);
        }
    }

    /**
     * Set the text of the badge with the default color or the color defined in the XML layout
     * @param text The string that you want the badge to display
     */
    public void setBadge(String text) {
        setBadge(text, badgeColor);
    }

    /**
     * Set the text and the color for the badge you are displaying
     * @param text The string that you want the badge to display
     * @param color The color you want the badge to be
     */
    public void setBadge(String text, int color) {
        badge = new BadgeDrawable(getContext(), text);
        badge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        badgeBoundsSet = false;

        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (badge != null) {
            if (!badgeBoundsSet) {
                layoutBadge();
            }
            badge.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        if (badge != null) {
            layoutBadge();
        }
    }

    private void layoutBadge() {
        Rect badgeBounds = badge.getBounds();
        Gravity.apply(badgeGravity,
                badge.getIntrinsicWidth(),
                badge.getIntrinsicHeight(),
                new Rect(0, 0, getWidth(), getHeight()),
                badgePaddingX != 0 ? badgePaddingX : badgePadding,
                badgePaddingY != 0 ? badgePaddingY : badgePadding,
                badgeBounds);
        badge.setBounds(badgeBounds);
        badgeBoundsSet = true;
    }
}