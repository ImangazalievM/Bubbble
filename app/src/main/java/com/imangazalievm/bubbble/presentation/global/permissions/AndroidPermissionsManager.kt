package com.imangazalievm.bubbble.presentation.global.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.afollestad.assent.AssentResult
import com.afollestad.assent.GrantResult
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidPermissionsManager @Inject constructor(

) : PermissionsManager {

    private var activity: Activity? = null
    private val pendingRequests = mutableListOf<Request>()

    fun attachActivity(activity: Activity) {
        this.activity = activity

        showNextPermissionRequest()
    }

    fun detachActivity() {
        this.activity = null
    }

    override suspend fun requestPermission(
        permission: Permission
    ): PermissionResult {
        return requestPermissions(permission).first()
    }

    override suspend fun requestPermissions(
        vararg permissions: Permission
    ): List<PermissionResult>  = suspendCoroutine { cont ->
        val resultListener = ResultListener {
            cont.resume(it)
        }
        val activity = activity
        if (activity != null) {
            activity.askForPermissions(*permissions) { results: AssentResult ->
                val results = permissions.map {
                    val result = results[it]
                    val isGranted = result == GrantResult.GRANTED
                    val isBlocked = result == GrantResult.PERMANENTLY_DENIED
                    PermissionResult(isGranted, isBlocked)
                }
                resultListener.onResult(results)
            }
        } else {
            pendingRequests.add(Request(permissions.toList(), resultListener))
        }
    }

    override suspend fun checkPermission(
        vararg permissions: Permission
    ): List<PermissionResult> = suspendCoroutine { cont ->
        val resultListener = ResultListener {
            cont.resume(it)
        }
        pendingRequests.add(Request(permissions.toList(), resultListener))
        showNextPermissionRequest()
    }

    override suspend fun isGranted(vararg permissions: Permission): Boolean {
        return checkPermission(*permissions).all { it.isGranted }
    }

    private fun showNextPermissionRequest() {
        activity?.let {
            if (pendingRequests.isNotEmpty()) {
                performRequest(it, pendingRequests.removeAt(0))
            }
        }
    }

    private fun performRequest(activity: Activity, request: Request) {
        val results = request.permissions.map { permission ->
            val permissionsCode = permission.value
            val isGranted =
                activity.checkCallingOrSelfPermission(permissionsCode) == PackageManager.PERMISSION_GRANTED
            val isBlocked = shouldShowRequestPermissionRationale(activity, permissionsCode)
            PermissionResult(isGranted, isBlocked)
        }
        request.resultListener.onResult(results)
        showNextPermissionRequest()
    }

    private class Request(
        val permissions: List<Permission>,
        val resultListener: ResultListener
    )

    fun interface ResultListener {
        fun onResult(results: List<PermissionResult>)
    }

}