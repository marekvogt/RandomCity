package com.marekvogt.randomcity.ui.list

val testRandomCityViewEntities = listOf(
    RandomCityViewEntity(
        name = "Warszawa",
        color = 0,
        emissionDateTime = "2021-01-01 00:00:00"
    ),
    RandomCityViewEntity(
        name = "Wrocław",
        color = 1,
        emissionDateTime = "2021-01-01 00:00:05"
    )
)

val testSelectedRandomCityViewEntity = RandomCityViewEntity(
    name = "Poznań",
    color = 2,
    emissionDateTime = "2021-01-01 00:00:10"
)

val testAnotherSelectedRandomCityViewEntity = RandomCityViewEntity(
    name = "Białystok",
    color = 3,
    emissionDateTime = "2021-01-01 00:00:15"
)
