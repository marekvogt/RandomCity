package com.marekvogt.randomcity.domain.city.repository

import com.marekvogt.randomcity.domain.city.model.RandomCity
import kotlinx.coroutines.flow.Flow

interface RandomCityRepository {

    fun observe(): Flow<List<RandomCity>>

    suspend fun add(randomCity: RandomCity)
}