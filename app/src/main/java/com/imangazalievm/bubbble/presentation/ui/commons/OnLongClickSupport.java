package com.imangazalievm.bubbble.presentation.ui.commons;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * Слушатель при долгом нажатии, для вызывает слушатель через определенный
 * переиод времени до тех пор, пока пользователь не уберет палец
 */
public class OnLongClickSupport {

    public interface OnLongClickListener {
        void onLongClick();
    }

    private static final long TAP_DELAY_MS = 200;

    private Handler handler;
    private Runnable longClickRunnable;
    private boolean isClickActive;

    public OnLongClickSupport(View view, OnLongClickListener listener) {
        this.handler = new Handler();
        this.isClickActive = false;

        longClickRunnable = () -> {
            listener.onLongClick();

            if (!isClickActive) return;
            handler.postDelayed(longClickRunnable, TAP_DELAY_MS);
        };

        view.setOnLongClickListener((v) -> {
            handler.postDelayed(longClickRunnable, TAP_DELAY_MS);
            isClickActive = true;
            return true;
        });
        view.setOnTouchListener((v, e) -> {
            v.onTouchEvent(e);
            if (e.getAction() == MotionEvent.ACTION_UP) {
                if (isClickActive) {
                    handler.removeCallbacks(longClickRunnable);
                    isClickActive = false;
                }
            }
            return false;
        });
    }

}
