package com.marekvogt.randomcity.ui.splash

import androidx.lifecycle.*
import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.common.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val randomCityEmissionInfoRepository: RandomCityEmissionInfoRepository,
) : ViewModel() {

    private val _navigateToCitiesEvent = MutableLiveData<Event<Unit>>()
    private val _errorEvent = MutableLiveData<Event<Throwable>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorEvent.value = Event(throwable)
    }

    val randomCityEmissionFailedEvent: LiveData<Event<Throwable>> = randomCityEmissionInfoRepository.observeEmissionFailed().asLiveData()

    val navigateToCitiesEvent: LiveData<Event<Unit>> = _navigateToCitiesEvent
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    fun waitForFirstCity() {
        viewModelScope.launch(coroutineExceptionHandler) {
            randomCityEmissionInfoRepository.observeFirstRandomCityEmitted().collect { isFirstRandomCityEmitted ->
                if (isFirstRandomCityEmitted) {
                    _navigateToCitiesEvent.value = Event(Unit)
                }
            }
        }
    }
}