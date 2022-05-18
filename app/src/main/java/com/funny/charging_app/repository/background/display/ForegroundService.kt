package com.funny.charging_app.repository.background.display

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.funny.charging_app.App
import com.funny.charging_app.R
import com.funny.charging_app.repository.background.lock.LockReceiver
import com.funny.charging_app.ui.main.MainActivity
import com.funny.charging_app.utils.registerReceiver

class ForegroundService : Service() {

    private var batteryReceiver: BatteryLevelReceiver? = null

    companion object {
        var instance: ForegroundService? = null
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        instance = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initForeground()
        registerReceiver(LockReceiver())
        registerReceiver(UsbReceiver())
        return super.onStartCommand(intent, flags, startId)
    }

    fun startBatteryReceiver() {
        if (batteryReceiver !== null) return
        batteryReceiver = BatteryLevelReceiver()
        registerReceiver(batteryReceiver!!)
    }

    fun stopBatteryReceiver() {
        if (batteryReceiver === null) return
        unregisterReceiver(batteryReceiver)
        batteryReceiver = null
    }

    private fun initForeground() {
        val pendingIntent: PendingIntent =
            Intent(App.instance, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    App.instance,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
        val notification = NotificationFactory.newNotification(
            R.mipmap.ic_launcher,
            getString(R.string.charging_animation),
            getString(R.string.working_in_background),
            pendingIntent
        )
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}