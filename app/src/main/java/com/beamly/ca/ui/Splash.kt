package com.beamly.ca.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.PremiumUserSdk
import com.beamly.ca.R
import com.beamly.ca.ui.main.MainActivity

class Splash : AppCompatActivity(R.layout.onboarding_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PremiumUserSdk.check(this) {
            if (PremiumUserSdk.isPremiumUser(this) && PremiumUserSdk.notRequiredPermission())
                PremiumUserSdk.onResult(this)
            else Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }
    }

}