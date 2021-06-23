package com.marekvogt.randomcity.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.databinding.ActivityMainBinding
import com.marekvogt.randomcity.ui.common.extension.autoClearedLateinit
import com.marekvogt.randomcity.ui.common.extension.observeEvent
import com.marekvogt.randomcity.ui.common.extension.showErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding by autoClearedLateinit<ActivityMainBinding>()

    private var mainNavController by autoClearedLateinit<NavController>()

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainNavController = findMainNavController()

        viewModel.setSideBySideDisplayed(findViewById<View>(R.id.secondContainer) != null)
        if (savedInstanceState != null) viewModel.restoreState()

        observeEvents()
    }

    private fun observeEvents() {
        viewModel.errorEvent.observeEvent(this) { throwable ->
            showErrorMessage(throwable)
        }
        viewModel.restoreRandomCityListEvent.observeEvent(this) {
            mainNavController.popBackStack(R.id.nav_graph, false)
        }
        viewModel.restoreRandomCityDetailsEvent.observeEvent(this) {
            mainNavController.navigate(R.id.action_random_city_list_to_random_city_details)
        }
        viewModel.randomCityEmissionFailedEvent.observeEvent(this) {
            showErrorMessage(it)
        }
    }

    override fun onBackPressed() {
        if (mainNavController.currentDestination?.id == R.id.randomCityDetailsFragment) {
            mainNavController.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun findMainNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        return navHostFragment.navController
    }
}