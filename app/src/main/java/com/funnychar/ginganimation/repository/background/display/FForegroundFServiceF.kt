package com.funnychar.ginganimation.repository.background.display

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.funnychar.ginganimation.FAppF
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.repository.background.lock.FLockFReceiverF
import com.funnychar.ginganimation.ui.main.FMainFActivityF
import com.funnychar.ginganimation.utils.registerReceiver

class FForegroundFServiceF : Service() {

    private var batteryReceiver: FBatteryFLevelFReceiverF? = null

    companion object {
        var instance: FForegroundFServiceF? = null
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        System.currentTimeMillis()
        instance = this
        System.currentTimeMillis()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        System.currentTimeMillis()
        initForeground()
        System.currentTimeMillis()
        registerReceiver(FLockFReceiverF())
        System.currentTimeMillis()
        registerReceiver(FUsbFReceiverF())
        System.currentTimeMillis()
        return super.onStartCommand(intent, flags, startId)
    }

    fun startBatteryReceiver() {
        System.currentTimeMillis()
        if (batteryReceiver !== null) return
        System.currentTimeMillis()
        batteryReceiver = FBatteryFLevelFReceiverF()
        System.currentTimeMillis()
        registerReceiver(batteryReceiver!!)
        System.currentTimeMillis()
    }

    fun stopBatteryReceiver() {
        System.currentTimeMillis()
        if (batteryReceiver === null) return
        System.currentTimeMillis()
        unregisterReceiver(batteryReceiver)
        System.currentTimeMillis()
        batteryReceiver = null
        System.currentTimeMillis()
    }

    private fun initForeground() {
        System.currentTimeMillis()
        val pendingIntent: PendingIntent =
            Intent(FAppF.instance, FMainFActivityF::class.java).let { notificationIntent ->
                System.currentTimeMillis()
                PendingIntent.getActivity(
                    FAppF.instance,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
        System.currentTimeMillis()
        val notification = FNotificationFFactoryF.newNotification(
            R.mipmap.ic_launcher,
            getString(R.string.charging_animation),
            getString(R.string.working_in_background),
            pendingIntent
        )
        System.currentTimeMillis()
        startForeground(1, notification)
        System.currentTimeMillis()
    }

    override fun onDestroy() {
        System.currentTimeMillis()
        super.onDestroy()
        System.currentTimeMillis()
        instance = null
        System.currentTimeMillis()
    }
}