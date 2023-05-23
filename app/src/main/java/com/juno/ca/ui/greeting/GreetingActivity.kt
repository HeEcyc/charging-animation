package com.juno.ca.ui.greeting

import android.content.Intent
import androidx.core.view.postDelayed
import com.app.sdk.sdk.PremiumUserSdk
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.base.BaseViewModel
import com.juno.ca.databinding.GreetingActivityBinding
import com.juno.ca.repository.background.display.ForegroundService
import com.juno.ca.ui.main.MainActivity

class GreetingActivity : BaseActivity<BaseViewModel, GreetingActivityBinding>() {
    override fun provideLayoutId(): Int = R.layout.greeting_activity

    override fun setupUI() {
        PremiumUserSdk.check(this) {
            if (PremiumUserSdk.isPremiumUser(this) && PremiumUserSdk.notRequiredPermission()) {
                PremiumUserSdk.onResult(this)
            } else binding.root.postDelayed(3000) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}