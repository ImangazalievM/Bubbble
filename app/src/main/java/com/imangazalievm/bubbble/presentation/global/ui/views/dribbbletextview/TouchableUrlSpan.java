package com.imangazalievm.bubbble.presentation.global.ui.views.dribbbletextview;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;

public class TouchableUrlSpan extends URLSpan {

    private static int[] STATE_PRESSED = new int[]{android.R.attr.state_pressed};
    private boolean isPressed;
    private int normalTextColor;
    private int pressedTextColor;

    public TouchableUrlSpan(String url,
                            ColorStateList textColor) {
        super(url);
        this.normalTextColor = textColor.getDefaultColor();
        this.pressedTextColor = textColor.getColorForState(STATE_PRESSED, normalTextColor);
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    @Override
    public void updateDrawState(TextPaint drawState) {
        drawState.setColor(isPressed ? pressedTextColor : normalTextColor);
        drawState.bgColor = Color.TRANSPARENT;
        drawState.setUnderlineText(!isPressed);
    }

}