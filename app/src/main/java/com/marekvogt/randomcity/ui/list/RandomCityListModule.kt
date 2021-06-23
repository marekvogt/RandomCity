package com.marekvogt.randomcity.ui.list

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
interface RandomCityListModule {

    @Binds
    @ActivityRetainedScoped
    fun bindRandomCityMapper(mapper: RandomCityLocalMapper): RandomCityMapper
}