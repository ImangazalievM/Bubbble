package com.bubbble.coreui.ui.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bubbble.coreui.di.coreUiEntrypoint
import com.bubbble.coreui.permissions.AndroidPermissionsManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import moxy.MvpAppCompatActivity
import kotlin.coroutines.CoroutineContext

abstract class BaseMvpActivity : MvpAppCompatActivity(), CoroutineScope {

    abstract val layoutRes: Int

    protected val parentJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val navigator = AppNavigator(this, -1)
    private val navigatorHolder = coreUiEntrypoint.navigatorHolder
    protected val permissionsManager by lazy {
        coreUiEntrypoint.permissionsManager as AndroidPermissionsManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
        permissionsManager.attachActivity(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent ?: return)
        val topFragment = supportFragmentManager.fragments.lastOrNull() as? BaseMvpFragment
        topFragment?.onNewBundle(intent.extras ?: return)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        permissionsManager.detachActivity()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        parentJob.cancel()
    }

    fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(textResId: Int) {
        showMessage(getString(textResId))
    }

}