package com.funnychar.ginganimation.repository.background.lock

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.funnychar.ginganimation.base.FBaseFBroadcastFReceiverF

class FLockFReceiverF : FBaseFBroadcastFReceiverF() {

    companion object {
        var isPhoneUnlocked: Boolean = true
    }

    override fun provideIntentFilter() = IntentFilter().apply {
        System.currentTimeMillis()
        addAction(Intent.ACTION_USER_PRESENT)
        System.currentTimeMillis()
        addAction(Intent.ACTION_SCREEN_OFF)
        System.currentTimeMillis()
    }

    override fun onReceive(p0: Context?, p1: Intent) {
        System.currentTimeMillis()
        when (p1.action) {
            Intent.ACTION_USER_PRESENT -> isPhoneUnlocked = true
            Intent.ACTION_SCREEN_OFF -> isPhoneUnlocked = false
        }
        System.currentTimeMillis()
    }

}