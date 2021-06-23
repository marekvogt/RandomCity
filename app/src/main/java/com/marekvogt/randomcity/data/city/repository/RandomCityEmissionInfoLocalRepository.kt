package com.marekvogt.randomcity.data.city.repository

import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.common.event.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class RandomCityEmissionInfoLocalRepository @Inject constructor() : RandomCityEmissionInfoRepository {

    private val isFirstRandomCityEmittedFlow = MutableStateFlow(false)
    private val isEmissionFailed = MutableStateFlow<Event<Throwable>?>(null)

    override fun observeFirstRandomCityEmitted(): Flow<Boolean> = isFirstRandomCityEmittedFlow
    override fun observeEmissionFailed(): Flow<Event<Throwable>> = isEmissionFailed.filterNotNull()

    override suspend fun setFirstRandomCityEmitted() {
        isFirstRandomCityEmittedFlow.emit(true)
    }

    override suspend fun setEmissionFailed(throwable: Throwable) {
        isEmissionFailed.emit(Event(throwable))
    }
}