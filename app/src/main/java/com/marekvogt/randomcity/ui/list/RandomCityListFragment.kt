package com.marekvogt.randomcity.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.databinding.FragmentRandomCityListBinding
import com.marekvogt.randomcity.ui.common.extension.autoClearedLateinit
import com.marekvogt.randomcity.ui.common.extension.observeEvent
import com.marekvogt.randomcity.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomCityListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding by autoClearedLateinit<FragmentRandomCityListBinding>()
    private var randomCityListAdapter by autoClearedLateinit<RandomCityListAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRandomCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeRandomCities()
        observeEvents()
    }

    private fun setupView() {
        randomCityListAdapter = RandomCityListAdapter { randomCity -> viewModel.selectCity(randomCity) }
        with(binding.rwRandomCities) {
            setHasFixedSize(true)
            adapter = randomCityListAdapter
        }
    }

    private fun observeRandomCities() {
        viewModel.randomCities.observe(viewLifecycleOwner) {
            randomCityListAdapter.submitList(it)
        }
    }

    private fun observeEvents() {
        viewModel.navigateToDetailsEvent.observeEvent(viewLifecycleOwner) {
//            navigateTo(R.id.action_random_city_list_to_random_city_details)
            findNavController().navigate(R.id.action_random_city_list_to_random_city_details)
        }
    }
}