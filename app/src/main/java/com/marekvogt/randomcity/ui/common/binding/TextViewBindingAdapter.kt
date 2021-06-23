package com.marekvogt.randomcity.ui.common.binding

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("textColorRes")
fun setBackgroundDrawable(view: TextView, @ColorRes colorRes: Int?) {
    colorRes?.let { view.setTextColor(ContextCompat.getColor(view.context, it)) }
}