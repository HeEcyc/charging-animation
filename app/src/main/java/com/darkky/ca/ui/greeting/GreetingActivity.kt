package com.darkky.ca.ui.greeting

import com.darkky.ca.R
import com.darkky.ca.base.BaseActivity
import com.darkky.ca.base.BaseViewModel
import com.darkky.ca.databinding.GreetingActivityBinding
import com.darkky.ca.repository.preferences.Preferences

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        binding.buttonStart.setOnClickListener { finish() }
        Preferences.hasShownGreeting = true
    }
}