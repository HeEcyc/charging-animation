package com.charginging.animationation.repository.background.boot

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.charginging.animationation.repository.background.display.ForegroundService

class BootCompletedReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context, p1: Intent?) {
        if (ForegroundService.instance === null)
            p0.startService(Intent(p0, ForegroundService::class.java))
    }

}