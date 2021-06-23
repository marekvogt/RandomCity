package com.marekvogt.randomcity.di

import com.marekvogt.randomcity.domain.common.datetime.CurrentLocalDateTimeProvider
import com.marekvogt.randomcity.domain.common.coroutine.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.time.LocalDateTime
import java.util.*


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideLocale(): Locale = Locale("pl")

    @Provides
    fun provideDispatchers(): Dispatchers = Dispatchers(
        ioDispatcher = kotlinx.coroutines.Dispatchers.IO,
        computationDispatcher = kotlinx.coroutines.Dispatchers.Default,
        mainDispatcher = kotlinx.coroutines.Dispatchers.Main
    )

    @Provides
    fun provideCurrentLocalDateTimeProvider(): CurrentLocalDateTimeProvider = object : CurrentLocalDateTimeProvider {
        override fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now()
    }

    @Provides
    @AppScope
    fun provideCoroutineScope(
        dispatchers: Dispatchers,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatchers.ioDispatcher)
}