package com.marekvogt.randomcity.data.city.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomCityDao {

    @Query("SELECT * FROM RandomCity")
    fun observeRandomCities(): Flow<List<RandomCityDto>>

    @Insert
    suspend fun insertRandomCity(randomCity: RandomCityDto)
}