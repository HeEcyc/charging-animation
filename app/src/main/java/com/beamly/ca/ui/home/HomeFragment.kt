package com.beamly.ca.ui.home

import androidx.fragment.app.viewModels
import com.beamly.ca.R
import com.beamly.ca.base.BaseFragment
import com.beamly.ca.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}