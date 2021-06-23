package com.marekvogt.randomcity.ui.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.marekvogt.randomcity.domain.common.event.Event

inline fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, crossinline onEmit: (T) -> (Unit)) {
    lifecycleOwner.observeEvent(this, onEmit)
}

inline fun <T> LifecycleOwner.observeEvent(value: LiveData<Event<T>>, crossinline onEmit: (T) -> (Unit)) {
    value.observe(this) { event ->
        event.getContentIfNotHandled()?.also {
            onEmit(it)
        }
    }
}