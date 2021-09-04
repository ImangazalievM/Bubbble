package com.bubbble.coreui.ui.views.badgedimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

public class BadgeDrawable extends Drawable {

    // save the bitmaps in a map so that we don't have to recreate them when
    // reusing the same text
    private static Map<String, Bitmap> bitmaps = new HashMap<>();

    private static final int TEXT_SIZE = 15;    // sp
    private static final int PADDING = 4;       // dp
    private static final int CORNER_RADIUS = 2; // dp
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final String TYPEFACE = "sans-serif-black";
    private static final int TYPEFACE_STYLE = Typeface.NORMAL;
    private int width;
    private int height;
    private final Paint paint;
    private final String text;

    public BadgeDrawable(Context context, String text) {
        this.text = text;

        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        final float density = dm.density;
        final float scaledDensity = dm.scaledDensity;
        final float padding = PADDING * density;
        final float cornerRadius = CORNER_RADIUS * density;

        final Rect textBounds = new Rect();
        final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint
                .SUBPIXEL_TEXT_FLAG);
        textPaint.setTypeface(Typeface.create(TYPEFACE, TYPEFACE_STYLE));
        textPaint.setTextSize(TEXT_SIZE * scaledDensity);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);

        height = (int) (padding + textBounds.height() + padding);
        width = (int) (padding + textBounds.width() + padding);

        if (bitmaps.get(text) == null) {

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setHasAlpha(true);

            final Canvas canvas = new Canvas(bitmap);
            final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            backgroundPaint.setColor(BACKGROUND_COLOR);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(0, 0, width, height, cornerRadius, cornerRadius,
                        backgroundPaint);
            } else {
                canvas.drawRect(0, 0, width, height, backgroundPaint);
            }

            // punch out the text leaving transparency
            textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawText(text, padding, height - padding, textPaint);

            bitmaps.put(text, bitmap);
        }

        paint = new Paint();
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmaps.get(text), getBounds().left, getBounds().top, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        // ignored
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return 0;
    }

}