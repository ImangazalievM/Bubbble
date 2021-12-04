package com.bubbble.coreui.navigationargs

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentNavDataProperty<Data> : NavDataProperty<Fragment, Data>() {
    override fun getData(thisRef: Fragment): Bundle? = thisRef.arguments
}