package com.funnychar.ginganimation

import com.app.sdk.AppApplication

class FAppF : AppApplication() {

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