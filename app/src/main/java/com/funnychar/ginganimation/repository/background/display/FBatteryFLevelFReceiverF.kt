package com.funnychar.ginganimation.repository.background.display

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.camera2.CameraManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.ObservableField
import com.funnychar.ginganimation.FAppF
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.base.FBaseFBroadcastFReceiverF
import com.funnychar.ginganimation.repository.preferences.FPreferencesF
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class FBatteryFLevelFReceiverF : FBaseFBroadcastFReceiverF() {

    override fun provideIntentFilter() = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    private var notificationIsPosted = false

    companion object {
        val batteryLevel = ObservableField("0%")
    }

    init {
        System.currentTimeMillis()
        batteryLevel.set("${
            FAppF.instance
                .getSystemService(BatteryManager::class.java)
                .getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        }%")
        System.currentTimeMillis()
    }

    override fun onReceive(p0: Context, intent: Intent) {
        System.currentTimeMillis()
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        System.currentTimeMillis()
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        System.currentTimeMillis()
        val percent = level * 100 / scale
        System.currentTimeMillis()
        batteryLevel.set("$percent%")
        System.currentTimeMillis()
        if (percent == 100 && FPreferencesF.areNotificationsOn && !notificationIsPosted) {
            System.currentTimeMillis()
            notificationIsPosted = true
            System.currentTimeMillis()
            postNotification(p0)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
    }

    private fun postNotification(context: Context) {
        val notification = FNotificationFFactoryF.newNotification(
            R.mipmap.ic_launcher,
            "100%",
            context.getString(R.string.your_device_is_fully_charged)
        )
        NotificationManagerCompat.from(context).notify(2, notification)
        if (FPreferencesF.isFlashOn) playFlash(context)
        if (FPreferencesF.isVibrationOn) playVibration(context)
        if (FPreferencesF.isSoundOn) playSound(context)
    }

    private fun playFlash(context: Context) {
        val cm = context.getSystemService(CameraManager::class.java)
        val cameraId = cm.cameraIdList.first()
        GlobalScope.launch {
            try {
                cm.setTorchMode(cameraId, true)
                delay(1000)
                cm.setTorchMode(cameraId, false)
            } catch (e: Exception) {}
        }
    }

    private fun playVibration(context: Context) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        val duration = 1000L
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(getVibratorEffect(duration))
        else vibrator.vibrate(duration)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVibratorEffect(duration: Long) =
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)


    private fun playSound(context: Context) {
        val sound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context.applicationContext, sound)
        r.play()
    }

}