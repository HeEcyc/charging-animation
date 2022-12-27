package com.dec.ces.ui.greeting

import com.dec.ces.R
import com.dec.ces.base.BaseActivity
import com.dec.ces.base.BaseViewModel
import com.dec.ces.databinding.GreetingActivityBinding
import com.dec.ces.repository.preferences.Preferences

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        binding.buttonStart.setOnClickListener { finish() }
        Preferences.hasShownGreeting = true
    }
}