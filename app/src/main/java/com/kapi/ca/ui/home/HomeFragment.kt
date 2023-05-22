package com.kapi.ca.ui.home

import androidx.fragment.app.viewModels
import com.kapi.ca.R
import com.kapi.ca.base.BaseFragment
import com.kapi.ca.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}