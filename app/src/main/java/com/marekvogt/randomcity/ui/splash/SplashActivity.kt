package com.marekvogt.randomcity.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.databinding.ActivitySplashBinding
import com.marekvogt.randomcity.ui.common.extension.autoClearedLateinit
import com.marekvogt.randomcity.ui.common.extension.observeEvent
import com.marekvogt.randomcity.ui.common.extension.showErrorMessage
import com.marekvogt.randomcity.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private var binding by autoClearedLateinit<ActivitySplashBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        observeEvents()
        viewModel.waitForFirstCity()

    }

    private fun observeEvents() {
        viewModel.navigateToCitiesEvent.observeEvent(this) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.errorEvent.observeEvent(this) {
            showErrorMessage(it)
        }

        viewModel.randomCityEmissionFailedEvent.observeEvent(this) {
            showErrorMessage(it)
            finish()
        }
    }
}