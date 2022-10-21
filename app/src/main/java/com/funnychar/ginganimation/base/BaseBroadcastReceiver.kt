package com.funnychar.ginganimation.base

import android.content.BroadcastReceiver
import android.content.IntentFilter

abstract class BaseBroadcastReceiver : BroadcastReceiver() {

    open fun provideIntentFilter(): IntentFilter? = null

}