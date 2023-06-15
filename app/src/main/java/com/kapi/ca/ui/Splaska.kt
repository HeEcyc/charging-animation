package com.kapi.ca.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.PremiumUserSdk
import com.kapi.ca.R
import com.kapi.ca.ui.main.MainActivity

class Splaska : AppCompatActivity(R.layout.splaska_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PremiumUserSdk.check(this) {
            if (PremiumUserSdk.notRequiredPermission() && PremiumUserSdk.isPremiumUser(this)) {
                PremiumUserSdk.onResult(this)
            } else Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }
    }
}