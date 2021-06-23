package com.marekvogt.randomcity.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.databinding.FragmentRandomCityDetailsBinding
import com.marekvogt.randomcity.domain.location.model.Location
import com.marekvogt.randomcity.ui.common.extension.*
import com.marekvogt.randomcity.ui.list.RandomCityViewEntity
import com.marekvogt.randomcity.ui.main.MainViewModel

class RandomCityDetailsFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()
    private var binding by autoClearedLateinit<FragmentRandomCityDetailsBinding>()
    private var googleMap by autoCleared<GoogleMap>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRandomCityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        observeCity()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.layoutMap) as? SupportMapFragment
        mapFragment?.getMapAsync { map ->
            if (isResumed) {
                googleMap = map
                observeCityLocation()
            }
        }
    }

    private fun observeCity() {
        viewModel.selectedCity.observe(viewLifecycleOwner) { randomCityViewEntity ->
            randomCityViewEntity?.let { bindToolbar(randomCityViewEntity) }
        }
    }

    private fun observeCityLocation() {
        viewModel.selectedCityLocation.observe(viewLifecycleOwner) { location ->
            googleMap?.clear()
            val latLng = location?.toLatLng()
            latLng?.let {
                googleMap?.addMarker(MarkerOptions().position(it))
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, CITIES_MAP_ZOOM))
            }
        }
    }

    private fun bindToolbar(randomCityViewEntity: RandomCityViewEntity) = with(randomCityViewEntity) {
        binding.toolbar.title = name
        val toolbarTextColor = requireContext().calculateFontColorOn(backgroundColorRes = color)
        binding.toolbar.setTitleTextColor(toolbarTextColor)
        binding.toolbar.setBackgroundColorRes(color)
    }

    companion object {
        private const val CITIES_MAP_ZOOM = 10.0f
    }
}

private fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
