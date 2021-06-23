package com.marekvogt.randomcity.ui.main

import androidx.lifecycle.*
import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.city.repository.RandomCityRepository
import com.marekvogt.randomcity.domain.common.coroutine.Dispatchers
import com.marekvogt.randomcity.domain.location.model.Location
import com.marekvogt.randomcity.domain.location.repository.LocationRepository
import com.marekvogt.randomcity.domain.common.event.Event
import com.marekvogt.randomcity.ui.list.RandomCityMapper
import com.marekvogt.randomcity.ui.list.RandomCityViewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val randomCityMapper: RandomCityMapper,
    randomCityRepository: RandomCityRepository,
    randomCityEmissionInfoRepository: RandomCityEmissionInfoRepository,
    dispatchers: Dispatchers,
) : ViewModel() {

    private val _selectedCity = MutableLiveData<RandomCityViewEntity?>()
    private val _navigateToDetailsEvent = MutableLiveData<Event<Unit>>()
    private val _restoreRandomCityListEvent = MutableLiveData<Event<Unit>>()
    private val _restoreRandomCityDetailsEvent = MutableLiveData<Event<Unit>>()
    private val _errorEvent = MutableLiveData<Event<Throwable>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorEvent.value = Event(throwable)
    }

    val randomCities: LiveData<List<RandomCityViewEntity>> = randomCityRepository.observe()
        .map { randomCityMapper.map(it) }
        .map { sortByName(it) }
        .flowOn(dispatchers.computationDispatcher)
        .catch { throwable -> coroutineExceptionHandler.handleException(currentCoroutineContext(), throwable) }
        .asLiveData()

    val selectedCity: LiveData<RandomCityViewEntity?> = _selectedCity
    val selectedCityLocation: LiveData<Location?> = _selectedCity.switchMap { findLocationBy(it?.name) }
    val navigateToDetailsEvent: LiveData<Event<Unit>> = _navigateToDetailsEvent
    val restoreRandomCityListEvent: LiveData<Event<Unit>> = _restoreRandomCityListEvent
    val restoreRandomCityDetailsEvent: LiveData<Event<Unit>> = _restoreRandomCityDetailsEvent
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent
    val randomCityEmissionFailedEvent: LiveData<Event<Throwable>> = randomCityEmissionInfoRepository.observeEmissionFailed().asLiveData()

    private var screenMode: ScreenMode by Delegates.observable(ScreenMode.UNDEFINED) { _, oldValue, newValue ->
        hasScreenModeChanged = oldValue != ScreenMode.UNDEFINED && oldValue != newValue
    }

    private var hasScreenModeChanged: Boolean = false

    fun selectCity(randomCityViewEntity: RandomCityViewEntity) {
        _selectedCity.value = randomCityViewEntity
        if (screenMode == ScreenMode.SINGLE) {
            _navigateToDetailsEvent.value = Event(Unit)
        }
    }

    fun restoreState() {
        when {
            screenMode == ScreenMode.SIDE_BY_SIDE -> _restoreRandomCityListEvent.value = Event(Unit)
            _selectedCity.value != null && hasScreenModeChanged -> _restoreRandomCityDetailsEvent.value = Event(Unit)
        }
    }

    fun setSideBySideDisplayed(isSideBySideDisplayed: Boolean) {
        screenMode = if (isSideBySideDisplayed) {
            ScreenMode.SIDE_BY_SIDE
        } else {
            ScreenMode.SINGLE
        }
    }

    private fun sortByName(cities: List<RandomCityViewEntity>): List<RandomCityViewEntity> {
        return cities.sortedBy { it.name }
    }

    private fun findLocationBy(cityName: String?): LiveData<Location?> = liveData(coroutineExceptionHandler) {
        val location = cityName?.let { locationRepository.findLocationBy(it) }
        emit(location)
    }
}

enum class ScreenMode {
    SINGLE, SIDE_BY_SIDE, UNDEFINED
}