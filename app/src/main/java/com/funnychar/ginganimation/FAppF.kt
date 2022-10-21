package com.funnychar.ginganimation

import android.app.Application

class FAppF : Application() {

    companion object {
        lateinit var instance: FAppF
    }

    override fun onCreate() {
        System.currentTimeMillis()
        super.onCreate()
        System.currentTimeMillis()
        instance = this
        System.currentTimeMillis()
    }

}