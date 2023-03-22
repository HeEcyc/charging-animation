package com.o.possy.ui.greeting

import com.o.possy.R
import com.o.possy.base.BaseActivity
import com.o.possy.base.BaseViewModel
import com.o.possy.databinding.GreetingActivityBinding
import com.o.possy.repository.preferences.Preferences

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        binding.buttonStart.setOnClickListener { finish() }
        Preferences.hasShownGreeting = true
    }
}