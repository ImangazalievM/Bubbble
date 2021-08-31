package com.bubbble.presentation.global.ui.base

import android.widget.Toast

abstract class BaseMvpDialogFragment : MvpAppCompatDialogFragment() {

    //protected val router by lazy { getGlobal<Navigator>() }
    //protected val screenResolver by lazy { getGlobal<ScreenResolver>() }

    fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(textResId: Int) {
        showMessage(getString(textResId))
    }

    //protected fun <T: Screen> getScreen() = screenResolver.getScreen<T>(this)

}
