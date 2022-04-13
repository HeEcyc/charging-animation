package com.ioscharginging.iosanimationation

import android.app.Application

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}

const val YEAR = 2022; const val MONTH = 9; const val DATE = 22