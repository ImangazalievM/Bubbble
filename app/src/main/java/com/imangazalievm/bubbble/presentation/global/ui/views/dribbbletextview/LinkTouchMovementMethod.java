package com.imangazalievm.bubbble.presentation.global.ui.views.dribbbletextview;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkTouchMovementMethod extends LinkMovementMethod {


    private static LinkTouchMovementMethod instance;
    private TouchableUrlSpan pressedSpan;

    public static MovementMethod getInstance() {
        if (instance == null)
            instance = new LinkTouchMovementMethod();

        return instance;
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        boolean handled = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressedSpan = getPressedSpan(textView, spannable, event);
            if (pressedSpan != null) {
                pressedSpan.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(pressedSpan),
                        spannable.getSpanEnd(pressedSpan));
                handled = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TouchableUrlSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (pressedSpan != null && touchedSpan != pressedSpan) {
                pressedSpan.setPressed(false);
                pressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (pressedSpan != null) {
                pressedSpan.setPressed(false);
                super.onTouchEvent(textView, spannable, event);
                handled = true;
            }
            pressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return handled;
    }

    private TouchableUrlSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent
            event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TouchableUrlSpan[] link = spannable.getSpans(off, off, TouchableUrlSpan.class);
        TouchableUrlSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

}