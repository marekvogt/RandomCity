package com.marekvogt.randomcity.domain.city.producer

import java.util.*
import javax.inject.Inject

class RandomNumberGenerator @Inject constructor() {

    private val random = Random()

    fun generate(bound: Int) = random.nextInt(bound)
}