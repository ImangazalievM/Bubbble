package com.bubbble.presentation.global.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

abstract class BaseMvpFragment : MvpAppCompatFragment()/*, ScreenResultListener*/ {

    companion object {
        private const val STATE_LAUNCH_FLAG = "state_launch_flag"
        private const val STATE_SCOPE_NAME = "state_scope_name"
        private const val STATE_SCOPE_WAS_CLOSED = "state_scope_was_closed"
    }

    abstract val layoutRes: Int

    private var instanceStateSaved: Boolean = false
    protected var lastBundle: Bundle? = null

  // protected val navigator by lazy { getGlobal<AndroidNavigator>() }
  // protected val screenResolver by lazy { getGlobal<ScreenResolver>() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            onNewBundle(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false

        val bundle = lastBundle
        if (bundle != null) {
            onNewBundle(bundle)
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun setNewBundle(bundle: Bundle) {
        lastBundle = bundle
        onNewBundle(bundle)
    }

    open fun onNewBundle(bundle: Bundle) {
        lastBundle = null
    }

  //  override fun onScreenResult(screenClass: Class<out Screen>, result: ScreenResult?) {
//
  //  }

    fun showMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(textResId: Int) {
        showMessage(getString(textResId))
    }

    //protected fun <T: Screen> getScreen() = screenResolver.getScreen<T>(this)

}