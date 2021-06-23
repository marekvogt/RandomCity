package com.marekvogt.randomcity.data.city.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RandomCity")
data class RandomCityDto(
    @PrimaryKey val emissionDateTime: String,
    val name: String,
    val color: String
)