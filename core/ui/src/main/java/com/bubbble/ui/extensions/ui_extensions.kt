package com.bubbble.ui.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

var ViewBinding.isVisible: Boolean
    get() = root.isVisible
    set(value) {
        root.isVisible = value
    }