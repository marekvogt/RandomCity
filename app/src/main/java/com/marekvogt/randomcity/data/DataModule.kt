package com.marekvogt.randomcity.data

import android.content.Context
import androidx.room.Room
import com.marekvogt.randomcity.data.city.persistence.RandomCityDao
import com.marekvogt.randomcity.data.city.repository.RandomCityEmissionInfoLocalRepository
import com.marekvogt.randomcity.data.city.repository.RandomCityRoomRepository
import com.marekvogt.randomcity.data.core.persistence.AppDatabase
import com.marekvogt.randomcity.data.location.repository.LocationGeocoderRepository
import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.city.repository.RandomCityRepository
import com.marekvogt.randomcity.domain.location.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DATABASE_NAME = "app_database"

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindRandomCityRepository(randomCityRoomRepository: RandomCityRoomRepository): RandomCityRepository

    @Binds
    @Singleton
    fun bindRandomCityEmissionInfoRepository(
        randomCityEmissionInfoLocalRepository: RandomCityEmissionInfoLocalRepository
    ): RandomCityEmissionInfoRepository

    @Binds
    @Singleton
    fun bindLocationRepository(locationGeocoderRepository: LocationGeocoderRepository): LocationRepository
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideRandomCityDao(appDatabase: AppDatabase): RandomCityDao = appDatabase.randomCityDao()
}