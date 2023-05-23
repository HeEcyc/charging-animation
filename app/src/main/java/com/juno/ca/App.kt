package com.juno.ca

import com.app.sdk.AppApplication

class App : AppApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}