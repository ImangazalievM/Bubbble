package com.bubbble.coreui.mvp

import android.util.Log
import moxy.MvpPresenter
import com.bubbble.coreui.di.coreUiEntrypoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.MvpView
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass

open class BasePresenter<V : BaseMvpView> : MvpPresenter<V>(), CoroutineScope {

    protected val router by lazy { coreUiEntrypoint.router }
    protected val errorHandler by lazy { coreUiEntrypoint.errorHandler }
    protected val resources by lazy { coreUiEntrypoint.resourcesManager }
    private val parentJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    init {
        Log.d("MID-Presenter", "Presenter: " + javaClass.simpleName)
    }

    override fun onDestroy() {
        parentJob.cancel()
    }

    open fun onBackPressed() {
        router.exit()
    }

    protected fun launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        showErrorMessage: Boolean = true,
        handledErrorTypes: List<KClass<out Throwable>> = emptyList(),
        customErrorHandler: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context) {
        try {
            block()
        } catch (error: Exception) {
            errorHandler.proceed(error, handledErrorTypes, customErrorHandler) {
                if (showErrorMessage && handledErrorTypes.isEmpty()) viewState.showMessage(it)
            }
        }
    }

    protected fun launchSafeNoProgress(
        context: CoroutineContext = EmptyCoroutineContext,
        showErrorMessage: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context) {
        try {
            block()
        } catch (error: Exception) {
            errorHandler.proceed(error, emptyList(), {}) {
                if (showErrorMessage) viewState.showMessage(it)
            }
        }
    }

}

