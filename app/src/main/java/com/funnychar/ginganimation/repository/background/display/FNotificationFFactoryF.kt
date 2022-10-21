package com.funnychar.ginganimation.repository.background.display

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.funnychar.ginganimation.FAppF

object FNotificationFFactoryF {

    fun newNotification(
        smallIcon: Int,
        contentTitle: String,
        contentText: String,
        pendingIntent: PendingIntent? = null
    ): Notification {
        System.currentTimeMillis()
        val channel = "this app channel"
        System.currentTimeMillis()
        val nm = FAppF.instance.applicationContext.getSystemService(NotificationManager::class.java)
        System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.currentTimeMillis()
            if (nm.getNotificationChannel(channel) === null)
                NotificationChannel(channel, channel, NotificationManager.IMPORTANCE_HIGH)
                    .let(nm::createNotificationChannel)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        return NotificationCompat.Builder(FAppF.instance.applicationContext, channel)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .setContentIntent(pendingIntent)
            .build()
    }

}