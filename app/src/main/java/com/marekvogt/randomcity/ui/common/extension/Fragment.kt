package com.marekvogt.randomcity.ui.common.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.marekvogt.randomcity.ui.common.lifecycle.AutoClearedLateinitValue
import com.marekvogt.randomcity.ui.common.lifecycle.AutoClearedNullableValue

fun <T : Any> Fragment.autoClearedLateinit(onClear: T.() -> Unit = {}) = AutoClearedLateinitValue(this, onClear)

fun <T : Any> Fragment.autoCleared() = AutoClearedNullableValue<T>(this)

private val Fragment.navigateController
    get() = findNavController()

fun Fragment.navigateTo(@IdRes actionId: Int, bundle: Bundle? = null) = with(navigateController) {
    currentDestination?.getAction(actionId)?.let {
        navigate(actionId, bundle)
    } ?: graph.findNode(actionId)?.let {
        navigate(it.id, bundle)
    }
}

