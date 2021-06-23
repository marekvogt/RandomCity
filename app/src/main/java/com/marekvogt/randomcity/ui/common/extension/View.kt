package com.marekvogt.randomcity.ui.common.extension

import android.view.View
import androidx.annotation.ColorRes

fun View.setBackgroundColorRes(@ColorRes colorRes: Int) = setBackgroundColor(context.color(colorRes))