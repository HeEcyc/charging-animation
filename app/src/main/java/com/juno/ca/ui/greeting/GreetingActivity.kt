package com.juno.ca.ui.greeting

import androidx.core.view.postDelayed
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.base.BaseViewModel
import com.juno.ca.databinding.GreetingActivityBinding
import com.juno.ca.repository.preferences.Preferences

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        binding.root.setOnClickListener { finish() }
        binding.root.postDelayed(3000, ::finish)
        Preferences.hasShownGreeting = true
    }
}