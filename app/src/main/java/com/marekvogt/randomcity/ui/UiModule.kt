package com.marekvogt.randomcity.ui

import com.marekvogt.randomcity.ui.common.formatter.DateFormatter
import com.marekvogt.randomcity.ui.common.formatter.EmissionDateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object UiModule {

    @Provides
    fun provideDateFormatter(): DateFormatter = EmissionDateFormatter()
}