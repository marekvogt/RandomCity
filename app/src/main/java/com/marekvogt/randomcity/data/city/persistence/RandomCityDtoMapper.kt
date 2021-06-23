package com.marekvogt.randomcity.data.city.persistence

import com.marekvogt.randomcity.domain.city.model.RandomCity
import java.time.LocalDateTime
import javax.inject.Inject

class RandomCityDtoTransformer @Inject constructor() {

    fun toDomain(randomCityDto: RandomCityDto): RandomCity = with(randomCityDto) {
        RandomCity(
            emissionDateTime = LocalDateTime.parse(emissionDateTime),
            name = RandomCity.Name.safeValueOf(name),
            color = RandomCity.Color.safeValueOf(color),
        )
    }

    fun fromDomain(randomCity: RandomCity): RandomCityDto = with(randomCity) {
        RandomCityDto(
            emissionDateTime = emissionDateTime.toString(),
            name = name.toString(),
            color = color.toString(),
        )
    }
}