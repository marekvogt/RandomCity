package com.marekvogt.randomcity.domain.city.producer

import com.marekvogt.randomcity.domain.city.model.RandomCity
import com.marekvogt.randomcity.domain.common.datetime.CurrentLocalDateTimeProvider
import javax.inject.Inject

class RandomCityGenerator @Inject constructor(
    private val randomNumberGenerator: RandomNumberGenerator,
    private val currentLocalDateTimeProvider: CurrentLocalDateTimeProvider
) {
    private val colors = RandomCity.Color.values()
    private val names = RandomCity.Name.values()

    fun generateRandomCity(): RandomCity = RandomCity(
        name = generateRandomName(),
        color = generateRandomColor(),
        emissionDateTime = currentLocalDateTimeProvider.getCurrentDateTime(),
    )

    private fun generateRandomName(): RandomCity.Name {
        val nameIndex = randomNumberGenerator.generate(names.size)
        return names[nameIndex]
    }

    private fun generateRandomColor(): RandomCity.Color {
        val colorIndex = randomNumberGenerator.generate(colors.size)
        return colors[colorIndex]
    }
}