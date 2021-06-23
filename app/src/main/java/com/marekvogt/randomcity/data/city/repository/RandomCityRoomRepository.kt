package com.marekvogt.randomcity.data.city.repository

import com.marekvogt.randomcity.data.city.persistence.RandomCityDao
import com.marekvogt.randomcity.data.city.persistence.RandomCityDto
import com.marekvogt.randomcity.data.city.persistence.RandomCityDtoTransformer
import com.marekvogt.randomcity.domain.city.model.RandomCity
import com.marekvogt.randomcity.domain.city.repository.RandomCityRepository
import com.marekvogt.randomcity.domain.common.coroutine.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomCityRoomRepository @Inject constructor(
    private val randomCityDao: RandomCityDao,
    private val randomCityDtoTransformer: RandomCityDtoTransformer,
    private val dispatchers: Dispatchers,
) : RandomCityRepository {

    override fun observe(): Flow<List<RandomCity>> =
        randomCityDao.observeRandomCities()
            .map(::mapToDomain)
            .flowOn(dispatchers.computationDispatcher)


    override suspend fun add(randomCity: RandomCity) {
        val randomCityDto = withContext(dispatchers.computationDispatcher) { randomCityDtoTransformer.fromDomain(randomCity) }
        randomCityDao.insertRandomCity(randomCityDto)
    }

    private fun mapToDomain(randomCitiesDto: List<RandomCityDto>) = randomCitiesDto.map { randomCityDtoTransformer.toDomain(it) }
}