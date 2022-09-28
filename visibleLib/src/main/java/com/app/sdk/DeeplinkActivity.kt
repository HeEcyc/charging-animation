package com.app.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.PlayerSdk


class DeeplinkActivity : AppCompatActivity(R.layout.empty) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerSdk.init(this, true)
        packageManager
            .getLaunchIntentForPackage(packageName)
            .let(::startActivity)
        finish()
    }
}
