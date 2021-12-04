package com.bubbble.coreui.navigationargs

import android.os.Bundle
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class NavDataProperty<Source, Data> : ReadOnlyProperty<Source, Data> {

    private val extractor = BundleNavDataExtractor()

    override fun getValue(thisRef: Source, property: KProperty<*>): Data {
        return extractor.getData(getData(thisRef))
    }

    abstract fun getData(thisRef: Source): Bundle?

}