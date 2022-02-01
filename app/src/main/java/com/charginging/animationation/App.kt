package com.charginging.animationation

import android.app.Application
import android.provider.Settings
import com.charginging.animationation.utils.APP_METRICA_API_KEY
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        activateAppMetrica()
        instance = this
    }

    private fun activateAppMetrica() {
        val appMetricaConfig: YandexMetricaConfig =
            YandexMetricaConfig.newConfigBuilder(APP_METRICA_API_KEY)
                .withLocationTracking(true)
                .withLogs()
                .withStatisticsSending(true)
                .build()
        YandexMetrica.activate(applicationContext, appMetricaConfig)
        YandexMetrica.enableActivityAutoTracking(this)
    }

}