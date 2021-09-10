package com.bubbble.coreui.ui.views.dribbbletextview

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.style.URLSpan
import android.text.TextPaint

open class TouchableUrlSpan(
    url: String?,
    textColor: ColorStateList
) : URLSpan(url) {

    private var isPressed = false
    private val normalTextColor: Int = textColor.defaultColor
    private val pressedTextColor: Int = textColor.getColorForState(STATE_PRESSED, normalTextColor)

    fun setPressed(isPressed: Boolean) {
        this.isPressed = isPressed
    }

    override fun updateDrawState(drawState: TextPaint) {
        drawState.color = if (isPressed) pressedTextColor else normalTextColor
        drawState.bgColor = Color.TRANSPARENT
        drawState.isUnderlineText = !isPressed
    }

    companion object {
        private val STATE_PRESSED = intArrayOf(R.attr.state_pressed)
    }

}