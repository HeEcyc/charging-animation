package com.charginging.animationation

import android.app.Application
import android.content.Intent
import android.os.Build
import com.charginging.animationation.repository.background.display.ForegroundService

class App : Application() {

    private val intentService get() = Intent(this, ForegroundService::class.java)

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(intentService)
        else startService(intentService)
    }

    fun stopForegroundService(){
        stopService(intentService)
    }
}