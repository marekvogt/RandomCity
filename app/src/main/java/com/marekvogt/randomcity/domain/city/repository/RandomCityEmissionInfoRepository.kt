package com.marekvogt.randomcity.domain.city.repository

import com.marekvogt.randomcity.domain.common.event.Event
import kotlinx.coroutines.flow.Flow

interface RandomCityEmissionInfoRepository {

    fun observeFirstRandomCityEmitted(): Flow<Boolean>
    
    fun observeEmissionFailed(): Flow<Event<Throwable>>

    suspend fun setFirstRandomCityEmitted()

    suspend fun setEmissionFailed(throwable: Throwable)
}