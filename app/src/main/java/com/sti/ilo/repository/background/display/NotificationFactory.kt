package com.sti.ilo.repository.background.display

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sti.ilo.App

object NotificationFactory {

    fun newNotification(
        smallIcon: Int,
        contentTitle: String,
        contentText: String,
        pendingIntent: PendingIntent? = null
    ): Notification {
        val channel = "this app channel"
        val nm = App.instance.applicationContext.getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (nm.getNotificationChannel(channel) === null)
                NotificationChannel(channel, channel, NotificationManager.IMPORTANCE_HIGH)
                    .let(nm::createNotificationChannel)
        }

        return NotificationCompat.Builder(App.instance.applicationContext, channel)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .setContentIntent(pendingIntent)
            .build()
    }

}