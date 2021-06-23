package com.marekvogt.randomcity.ui.list

import android.content.Context
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.domain.city.model.RandomCity
import com.marekvogt.randomcity.ui.common.formatter.DateFormatter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface RandomCityMapper {
    fun map(randomCities: List<RandomCity>): List<RandomCityViewEntity>
}

class RandomCityLocalMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dateFormatter: DateFormatter
) : RandomCityMapper {
    override fun map(randomCities: List<RandomCity>): List<RandomCityViewEntity> = randomCities.mapNotNull { randomCity ->
        val nameRes = mapName(randomCity.name)
        val color = mapColor(randomCity.color)
        if (nameRes == null || color == null) {
            null
        } else {
            RandomCityViewEntity(
                name = context.getString(nameRes),
                color = color,
                emissionDateTime = dateFormatter.format(randomCity.emissionDateTime),
            )
        }
    }

    private fun mapName(name: RandomCity.Name): Int? = when (name) {
        RandomCity.Name.BIALYSTOK -> R.string.l_bialystok
        RandomCity.Name.CRACOW -> R.string.l_cracow
        RandomCity.Name.GDANSK -> R.string.l_gdansk
        RandomCity.Name.KATOWICE -> R.string.l_katowice
        RandomCity.Name.POZNAN -> R.string.l_poznan
        RandomCity.Name.WARSAW -> R.string.l_warsaw
        RandomCity.Name.WROCLAW -> R.string.l_wroclaw
        else -> null
    }

    private fun mapColor(color: RandomCity.Color): Int? = when (color) {
        RandomCity.Color.BLACK -> R.color.black
        RandomCity.Color.BLUE -> R.color.blue
        RandomCity.Color.GREEN -> R.color.green
        RandomCity.Color.RED -> R.color.red
        RandomCity.Color.WHITE -> R.color.white
        RandomCity.Color.YELLOW -> R.color.yellow
        else -> null
    }

}