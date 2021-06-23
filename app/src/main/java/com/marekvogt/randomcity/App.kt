package com.marekvogt.randomcity

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.marekvogt.randomcity.domain.city.producer.RandomCityProducer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), LifecycleObserver {

    @Inject
    lateinit var randomCityProducer: RandomCityProducer

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppWentForeground(): Unit = randomCityProducer.start()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppWentBackground(): Unit = randomCityProducer.stop()
}

