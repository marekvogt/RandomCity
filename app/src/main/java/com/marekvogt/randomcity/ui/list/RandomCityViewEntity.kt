package com.marekvogt.randomcity.ui.list

import androidx.annotation.ColorRes
import java.io.Serializable

data class RandomCityViewEntity(
    val name: String,
    @ColorRes val color: Int,
    val emissionDateTime: String,
) : Serializable