package com.marekvogt.randomcity.data.core.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marekvogt.randomcity.data.city.persistence.RandomCityDao
import com.marekvogt.randomcity.data.city.persistence.RandomCityDto

@Database(
    entities = [RandomCityDto::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun randomCityDao(): RandomCityDao
}