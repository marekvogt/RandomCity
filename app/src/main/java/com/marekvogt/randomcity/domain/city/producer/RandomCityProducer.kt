package com.marekvogt.randomcity.domain.city.producer

import com.marekvogt.randomcity.di.AppScope
import com.marekvogt.randomcity.domain.city.model.RandomCity
import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.city.repository.RandomCityRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class RandomCityProducer @Inject constructor(
    private val randomCityRepository: RandomCityRepository,
    private val randomCityEmissionInfoRepository: RandomCityEmissionInfoRepository,
    private val randomCityGenerator: RandomCityGenerator,
    @AppScope private val coroutineScope: CoroutineScope,
) {
    private var job: Job? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        runBlocking { randomCityEmissionInfoRepository.setEmissionFailed(throwable) }
    }

    fun start() {
        job?.cancel()
        job = emitRandomCities()
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    private fun emitRandomCities() = coroutineScope.launch(coroutineExceptionHandler) {
        delay(EMIT_INTERVAL)
        saveRandomCity(generateRandomCity())
        randomCityEmissionInfoRepository.setFirstRandomCityEmitted()
        while (isActive) {
            delay(EMIT_INTERVAL)
            saveRandomCity(generateRandomCity())
        }
    }

    private fun generateRandomCity() = randomCityGenerator.generateRandomCity()

    private suspend fun saveRandomCity(randomCity: RandomCity) = randomCityRepository.add(randomCity)

    companion object {
        private const val EMIT_INTERVAL = 5_000L
    }
}