package com.bubbble.coreui.ui.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bubbble.di.extensions.appComponent
import com.bubbble.coreui.permissions.AndroidPermissionsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseMvpActivity : MvpAppCompatActivity(), CoroutineScope {

    abstract val layoutRes: Int

    protected val parentJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    //protected val navigator by lazy { getGlobal<AndroidNavigator>() }
    //protected val navigationContextBinder by lazy { getGlobal<NavigationContextBinder>() }
    //protected val screenResolver by lazy { getGlobal<ScreenResolver>() }
    protected val permissionsManager by lazy {
        appComponent.permissionsManager as AndroidPermissionsManager
    }
    //private val activityResultHandler by lazy { getGlobal<ActivityResultHandler>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        permissionsManager.attachActivity(this)
        bindNavigationContext()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent ?: return)
        val topFragment = supportFragmentManager.fragments.lastOrNull() as? BaseMvpFragment
        topFragment?.onNewBundle(intent.extras ?: return)
    }

    override fun onPause() {
        //unbindNavigationContext()
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

    override fun onBackPressed() {
        //val fragmentNavigator = fragmentNavigator
        //if (fragmentNavigator == null) {
        //    finish()
        //    return
        //}
        //val topFragment = supportFragmentManager.fragments.lastOrNull() as? BaseMvpFragment
        //val canGoBack = fragmentNavigator.canGoBack()
        //if (topFragment!!.onBackPressed()) {
        //    return
        //}
//
        //if (canGoBack) {
        //    navigator.goBack()
        //} else finish()
    }

    //these methods are created to customize it in child classes if it needed
    protected open fun bindNavigationContext() {
        //navigationContextBinder.bind(createNavigationContext().build())
    }

 //  protected open fun createNavigationContext(): NavigationContext.Builder {
 //      return NavigationContext.Builder(this, getGlobal())
 //              .screenResultListener { screenClass: Class<out Screen>, result: ScreenResult? ->
 //                  onScreenResult(screenClass, result)
 //              }
 //  }

 //  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
 //      super.onActivityResult(requestCode, resultCode, data)
 //      activityResultHandler.onActivityResult(requestCode, resultCode, data) // handle result
 //  }

    /**
     * @param result Can be null if a screen has finished without no result.
     */
 //  protected open fun onScreenResult(screenClass: Class<out Screen>, result: ScreenResult?) {

 //  }

 //  protected open fun unbindNavigationContext() {
 //      navigationContextBinder.unbind(this)
 //  }

    //It will be valid only for 'onDestroy()' method
    private fun needCloseScope(): Boolean =
            when {
                isChangingConfigurations -> false
                isFinishing -> true
                else -> false
            }

    //protected fun <T : Screen> getScreen() = screenResolver.getScreen<T>(this)

}