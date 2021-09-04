package com.bubbble.presentation.global.ui.views.dribbbletextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class DribbbleTextView extends AppCompatTextView {

    private static int[] STATE_ENABLED = new int[]{android.R.attr.state_enabled};
    private static int[] STATE_PRESSED = new int[]{android.R.attr.state_pressed};

    public interface OnLinkClickListener {
        void onLinkClick(String url);
    }

    public interface OnUserSelectedListener {
        void onUserSelected(long useId);
    }

    private ColorStateList linkTextColors;
    private OnLinkClickListener onLinkClickListener;
    private OnUserSelectedListener onUserSelectedListener;

    public DribbbleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setMovementMethod(LinkTouchMovementMethod.getInstance());
        setFocusable(false);
        setClickable(false);
        setLongClickable(false);
        initLnkTextColors();
    }

    private void initLnkTextColors() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                getLinkTextColors().getColorForState(STATE_ENABLED, Color.MAGENTA),
                getHighlightColor()
        };

        linkTextColors = new ColorStateList(states, colors);
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.onLinkClickListener = onLinkClickListener;
    }

    public void setOnUserSelectedListener(OnUserSelectedListener onUserSelectedListener) {
        this.onUserSelectedListener = onUserSelectedListener;
    }

    public void setHtmlText(String text) {
        if (TextUtils.isEmpty(text)) return;
        final Spanned descriptionText = parseDribbbleHtml(text, linkTextColors);
        setText(descriptionText);
    }

    public Spanned parseDribbbleHtml(String input, ColorStateList linkTextColor) {
        SpannableStringBuilder ssb = parseHtml(input, linkTextColor);
        LinkSpan[] linkSpans = ssb.getSpans(0, ssb.length(), LinkSpan.class);
        for (LinkSpan urlSpan : linkSpans) {
            int start = ssb.getSpanStart(urlSpan);
            if (ssb.subSequence(start, start + 1).toString().equals("@")) {
                int end = ssb.getSpanEnd(urlSpan);
                ssb.removeSpan(urlSpan);
                UserSpan userSpan = new UserSpan(urlSpan.getURL(), linkTextColor) {
                    @Override
                    public void onClick(long userId) {
                        if (onUserSelectedListener != null) {
                            onUserSelectedListener.onUserSelected(userId);
                        }
                    }
                };
                ssb.setSpan(userSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ssb;
    }

    /**
     * Parse the given input using {@link TouchableUrlSpan}s rather than vanilla {@link URLSpan}s
     * so that they respond to touch.
     */
    public SpannableStringBuilder parseHtml(
            String input,
            ColorStateList linkTextColor) {
        SpannableStringBuilder spanned = fromHtml(input);

        // strip any trailing newlines
        while (spanned.charAt(spanned.length() - 1) == '\n') {
            spanned = spanned.delete(spanned.length() - 1, spanned.length());
        }

        return linkifyPlainLinks(spanned, linkTextColor);
    }

    private SpannableStringBuilder fromHtml(String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return (SpannableStringBuilder) Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return (SpannableStringBuilder) Html.fromHtml(input);
        }
    }

    private SpannableStringBuilder linkifyPlainLinks(CharSequence input, ColorStateList linkTextColor) {
        final SpannableString plainLinks = new SpannableString(input); // copy of input

        // Linkify doesn't seem to work as expected on M+
        // TODO: figure out why
        //Linkify.addLinks(plainLinks, Linkify.WEB_URLS);

        final URLSpan[] urlSpans = plainLinks.getSpans(0, plainLinks.length(), URLSpan.class);

        // add any plain links to the output
        final SpannableStringBuilder ssb = new SpannableStringBuilder(input);
        for (URLSpan urlSpan : urlSpans) {
            ssb.removeSpan(urlSpan);
            LinkSpan linkSpan = new LinkSpan(urlSpan.getURL(), linkTextColor) {
                @Override
                public void onClick(String url) {
                    if (onLinkClickListener != null) {
                        onLinkClickListener.onLinkClick(url);
                    }
                }
            };
            ssb.setSpan(linkSpan,
                    plainLinks.getSpanStart(urlSpan),
                    plainLinks.getSpanEnd(urlSpan),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ssb;
    }

}
