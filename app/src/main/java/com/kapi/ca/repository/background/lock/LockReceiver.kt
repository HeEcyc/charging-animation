package com.kapi.ca.repository.background.lock

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.kapi.ca.base.BaseBroadcastReceiver

class LockReceiver : BaseBroadcastReceiver() {

    companion object {
        var isPhoneUnlocked: Boolean = true
    }

    override fun provideIntentFilter() = IntentFilter().apply {
        addAction(Intent.ACTION_USER_PRESENT)
        addAction(Intent.ACTION_SCREEN_OFF)
    }

    override fun onReceive(p0: Context?, p1: Intent) {
        when (p1.action) {
            Intent.ACTION_USER_PRESENT -> isPhoneUnlocked = true
            Intent.ACTION_SCREEN_OFF -> isPhoneUnlocked = false
        }
    }

}