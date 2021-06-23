package com.marekvogt.randomcity.ui.common.extension

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.marekvogt.randomcity.R
import kotlin.math.sqrt

@ColorInt
fun Context.color(@ColorRes colorResId: Int): Int = ContextCompat.getColor(this, colorResId)

@ColorInt
fun Context.calculateFontColorOn(@ColorRes backgroundColorRes: Int): Int {
    val backgroundColor = color(backgroundColorRes)
    val luminance = calculateLuminanceFor(backgroundColor)
    val w3cBorderLuminance = sqrt(1.05 * 0.05) - 0.05
    return color(if (luminance < w3cBorderLuminance) R.color.black else R.color.white)
}

fun Context.showErrorMessage(throwable: Throwable) =
    Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()

private fun calculateLuminanceFor(@ColorInt color: Int) =
    1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255