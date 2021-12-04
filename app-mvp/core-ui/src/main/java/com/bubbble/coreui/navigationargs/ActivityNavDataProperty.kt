package com.bubbble.coreui.navigationargs

import android.app.Activity
import android.os.Bundle

class ActivityNavDataProperty<Data> : NavDataProperty<Activity, Data>() {
    override fun getData(thisRef: Activity): Bundle? = thisRef.intent.extras
}

