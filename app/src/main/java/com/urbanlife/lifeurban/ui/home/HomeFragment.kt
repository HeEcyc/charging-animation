package com.urbanlife.lifeurban.ui.home

import androidx.fragment.app.viewModels
import com.urbanlife.lifeurban.R
import com.urbanlife.lifeurban.base.BaseFragment
import com.urbanlife.lifeurban.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}