package com.charful.cheerge

import android.app.Application
import com.app.sdk.sdk.PlayerSdk

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}