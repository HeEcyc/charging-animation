package com.smooth.battery.ui.home

import androidx.fragment.app.viewModels
import com.smooth.battery.R
import com.smooth.battery.base.BaseFragment
import com.smooth.battery.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}