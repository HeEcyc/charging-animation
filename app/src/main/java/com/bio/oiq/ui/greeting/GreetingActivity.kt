package com.bio.oiq.ui.greeting

import androidx.core.view.postDelayed
import com.bio.oiq.R
import com.bio.oiq.base.BaseActivity
import com.bio.oiq.base.BaseViewModel
import com.bio.oiq.databinding.GreetingActivityBinding
import com.bio.oiq.repository.preferences.Preferences

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        Preferences.hasShownGreeting = true
        binding.root.postDelayed(2000) { finish() }
    }
}