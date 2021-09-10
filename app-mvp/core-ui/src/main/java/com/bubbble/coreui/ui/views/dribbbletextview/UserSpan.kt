package com.bubbble.coreui.ui.views.dribbbletextview

import android.content.res.ColorStateList
import android.view.View

abstract class UserSpan(
    url: String,
    textColor: ColorStateList
) : TouchableUrlSpan(url, textColor) {

    override fun onClick(widget: View) {
        onClick(url)
    }

    abstract fun onClick(url: String?)

}