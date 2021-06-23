package com.marekvogt.randomcity.ui.common.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedLateinitValue<T : Any>(lifecycleOwner: LifecycleOwner, onClear: T.() -> Unit) : ReadWriteProperty<LifecycleOwner, T> {
    private var _value: T? = null

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value?.let(onClear)
                _value = null
            }
        })
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T =
        _value ?: throw IllegalStateException("should never call auto-cleared-lateinit-value get when it might not be available")

    override fun setValue(thisRef: LifecycleOwner, property: KProperty<*>, value: T) {
        _value = value
    }
}