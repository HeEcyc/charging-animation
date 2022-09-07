package com.rocket.charge.ui.home

import androidx.fragment.app.viewModels
import com.rocket.charge.R
import com.rocket.charge.base.BaseFragment
import com.rocket.charge.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}