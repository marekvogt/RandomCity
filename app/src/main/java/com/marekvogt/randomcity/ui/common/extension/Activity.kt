package com.marekvogt.randomcity.ui.common.extension

import androidx.appcompat.app.AppCompatActivity
import com.marekvogt.randomcity.ui.common.lifecycle.AutoClearedLateinitValue

fun <T : Any> AppCompatActivity.autoClearedLateinit(onClear: T.() -> Unit = {}) = AutoClearedLateinitValue(this, onClear)