package com.marekvogt.randomcity.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marekvogt.randomcity.domain.city.model.RandomCity
import com.marekvogt.randomcity.domain.city.model.testRandomCities
import com.marekvogt.randomcity.domain.city.repository.RandomCityEmissionInfoRepository
import com.marekvogt.randomcity.domain.city.repository.RandomCityRepository
import com.marekvogt.randomcity.domain.common.event.Event
import com.marekvogt.randomcity.domain.common.testDispatchers
import com.marekvogt.randomcity.domain.location.model.testLocation
import com.marekvogt.randomcity.domain.location.repository.LocationRepository
import com.marekvogt.randomcity.ui.list.*
import com.marekvogt.randomcity.util.extension.getOrAwaitValue
import com.marekvogt.randomcity.util.mock.anyKotlinObject
import com.marekvogt.randomcity.util.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val randomCitiesEmitter = MutableStateFlow<List<RandomCity>>(emptyList())
    private val randomCityFailedEmitter = MutableStateFlow<Event<Throwable>?>(null)
    private val randomCityRepository: RandomCityRepository = mock(RandomCityRepository::class.java)
    private val randomCityEmissionInfoRepository: RandomCityEmissionInfoRepository = mock(RandomCityEmissionInfoRepository::class.java)
    private val locationRepository: LocationRepository = mock(LocationRepository::class.java)
    private val randomCityMapper: RandomCityMapper = mock(RandomCityMapper::class.java)

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeEach() {
        `when`(randomCityRepository.observe()).thenAnswer { randomCitiesEmitter }
        `when`(randomCityEmissionInfoRepository.observeEmissionFailed()).thenAnswer { randomCityFailedEmitter }
        `when`(randomCityMapper.map(testRandomCities)).thenAnswer { testRandomCityViewEntities }

        viewModel = MainViewModel(
            randomCityRepository = randomCityRepository,
            randomCityEmissionInfoRepository = randomCityEmissionInfoRepository,
            locationRepository = locationRepository,
            randomCityMapper = randomCityMapper,
            dispatchers = testDispatchers,
        )
    }

    @Test
    fun `should emit no cities after initialization`(): Unit = runBlocking {
        val cities = viewModel.randomCities.getOrAwaitValue()

        cities shouldBe emptyList()
    }

    @Test
    fun `should emit already emitted cities`(): Unit = runBlocking {
        randomCitiesEmitter.emit(testRandomCities)

        val cities = viewModel.randomCities.getOrAwaitValue()

        cities shouldBeEqualTo testRandomCityViewEntities
    }

    @Test
    fun `should emit particular random city when selected`(): Unit = runBlocking {
        viewModel.selectCity(testSelectedRandomCityViewEntity)
        val selectedCity = viewModel.selectedCity.getOrAwaitValue()

        selectedCity shouldBeEqualTo testSelectedRandomCityViewEntity
    }

    @Test
    fun `should update selected city when another one is chosen`() {
        viewModel.selectCity(testSelectedRandomCityViewEntity)
        viewModel.selectCity(testAnotherSelectedRandomCityViewEntity)
        val selectedCity = viewModel.selectedCity.getOrAwaitValue()

        selectedCity shouldBeEqualTo testAnotherSelectedRandomCityViewEntity
    }

    @Test
    fun `should emit city location when city is selected`(): Unit = runBlocking {
        `when`(locationRepository.findLocationBy(anyKotlinObject())).thenAnswer { testLocation }

        viewModel.selectCity(testSelectedRandomCityViewEntity)
        val selectedCityLocation = viewModel.selectedCityLocation.getOrAwaitValue()

        selectedCityLocation shouldBeEqualTo testLocation
    }

    @Test
    fun `should restore random city list when orientation changed in side by side screen mode`(): Unit = runBlocking {
        viewModel.setSideBySideDisplayed(false)

        viewModel.setSideBySideDisplayed(true)
        viewModel.restoreState()
        val event = viewModel.restoreRandomCityListEvent.getOrAwaitValue()

        event.getContentIfNotHandled() shouldNotBe null
    }

    @Test
    fun `should restore selected city details when orientation changed in single screen mode`(): Unit = runBlocking {
        viewModel.setSideBySideDisplayed(true)
        viewModel.selectCity(testSelectedRandomCityViewEntity)

        viewModel.setSideBySideDisplayed(false)
        viewModel.restoreState()
        val event = viewModel.restoreRandomCityDetailsEvent.getOrAwaitValue()

        event.getContentIfNotHandled() shouldNotBe null
    }

    @Test
    fun `should navigate to details when city is selected in single screen mode`(): Unit = runBlocking {
        viewModel.setSideBySideDisplayed(false)

        viewModel.selectCity(testSelectedRandomCityViewEntity)
        val event = viewModel.navigateToDetailsEvent.getOrAwaitValue()

        event.getContentIfNotHandled() shouldNotBe null
    }

    @Test
    fun `should emit random city emission failed event when random producer failed`(): Unit = runBlocking {
        randomCityFailedEmitter.emit(Event(Exception()))

        val event = viewModel.randomCityEmissionFailedEvent.getOrAwaitValue()

        event.getContentIfNotHandled() shouldNotBe null
    }
}