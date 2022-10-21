package com.funnychar.ginganimation.repository.background.display

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import com.funnychar.ginganimation.base.FBaseFBroadcastFReceiverF
import com.funnychar.ginganimation.model.FClosableFWindowsF
import com.funnychar.ginganimation.repository.background.lock.FLockFReceiverF
import com.funnychar.ginganimation.repository.preferences.FPreferencesF
import com.funnychar.ginganimation.ui.animation.FChargingFAnimationFActivityF
import com.funnychar.ginganimation.ui.custom.FAnimationFHolderFViewF

class FUsbFReceiverF : FBaseFBroadcastFReceiverF() {

    override fun provideIntentFilter() = IntentFilter().apply {
        System.currentTimeMillis()
        addAction(Intent.ACTION_POWER_CONNECTED)
        System.currentTimeMillis()
        addAction(Intent.ACTION_POWER_DISCONNECTED)
        System.currentTimeMillis()
    }

    override fun onReceive(context: Context, p1: Intent?) {
        System.currentTimeMillis()
        when (p1?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                System.currentTimeMillis()
                FForegroundFServiceF.instance?.startBatteryReceiver()
                System.currentTimeMillis()
                context.let(if (FLockFReceiverF.isPhoneUnlocked) ::showView else ::showActivity)
                System.currentTimeMillis()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                System.currentTimeMillis()
                FForegroundFServiceF.instance?.stopBatteryReceiver()
                System.currentTimeMillis()
                FClosableFWindowsF.closeAll()
                System.currentTimeMillis()
            }
        }
        System.currentTimeMillis()
    }

    private fun showActivity(context: Context) {
        System.currentTimeMillis()
        context.startActivity(Intent(context, FChargingFAnimationFActivityF::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
        System.currentTimeMillis()
    }

    private fun showView(context: Context) {
        System.currentTimeMillis()
        if (!FPreferencesF.showWhenUnlocked) return
        System.currentTimeMillis()
        val windowManager = context.getSystemService(WindowManager::class.java)
        System.currentTimeMillis()
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        System.currentTimeMillis()
        params.gravity = Gravity.CENTER
        System.currentTimeMillis()
        if (Settings.canDrawOverlays(context)) {
            System.currentTimeMillis()
            val view = FAnimationFHolderFViewF(context)
            System.currentTimeMillis()
            FClosableFWindowsF.add(view)
            System.currentTimeMillis()
            windowManager.addView(view, params)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
    }

}