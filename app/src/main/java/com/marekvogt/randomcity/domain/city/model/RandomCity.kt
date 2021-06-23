package com.marekvogt.randomcity.domain.city.model

import java.io.Serializable
import java.time.LocalDateTime

data class RandomCity(
    val name: Name,
    val color: Color,
    val emissionDateTime: LocalDateTime
) : Serializable {

    enum class Color {
        YELLOW, GREEN, BLUE, RED, BLACK, WHITE, UNKNOWN;

        companion object {
            fun safeValueOf(rawColor: String) = values().find { it.name == rawColor } ?: UNKNOWN
        }
    }

    enum class Name {
        GDANSK, WARSAW, POZNAN, BIALYSTOK, WROCLAW, KATOWICE, CRACOW, UNKNOWN;

        companion object {
            fun safeValueOf(rawCity: String) = values().find { it.name == rawCity } ?: UNKNOWN
        }
    }
}