package com.marekvogt.randomcity.domain.city.model

import java.time.LocalDateTime

val testRandomCities = listOf(
    RandomCity(
        name = RandomCity.Name.WARSAW,
        color = RandomCity.Color.BLUE,
        emissionDateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0)
    ),
    RandomCity(
        name = RandomCity.Name.WROCLAW,
        color = RandomCity.Color.WHITE,
        emissionDateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 5)
    )
)