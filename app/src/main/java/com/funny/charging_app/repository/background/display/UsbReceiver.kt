package com.funny.charging_app.repository.background.display

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.WindowManager
import com.funny.charging_app.R
import com.funny.charging_app.base.BaseBroadcastReceiver
import com.funny.charging_app.model.ClosableWindows
import com.funny.charging_app.repository.background.lock.LockReceiver
import com.funny.charging_app.repository.preferences.Preferences
import com.funny.charging_app.ui.animation.ChargingAnimationActivity
import com.funny.charging_app.ui.custom.AnimationHolderView


class UsbReceiver : BaseBroadcastReceiver() {

    override fun provideIntentFilter() = IntentFilter().apply {
        addAction(Intent.ACTION_POWER_CONNECTED)
        addAction(Intent.ACTION_POWER_DISCONNECTED)
    }

    override fun onReceive(context: Context, p1: Intent?) {
        when (p1?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                ForegroundService.instance?.startBatteryReceiver()
                context.let(if (LockReceiver.isPhoneUnlocked) ::showView else ::showActivity)
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                ForegroundService.instance?.stopBatteryReceiver()
                ClosableWindows.closeAll()
            }
        }
    }

    private fun showActivity(context: Context) {
        Intent(context, ChargingAnimationActivity::class.java)
            .addFlags(FLAG_ACTIVITY_NEW_TASK)
            .let(context::startActivity)
    }

    private fun showView(context: Context) {
        if (!Preferences.showWhenUnlocked) return
        val windowManager = context.getSystemService(WindowManager::class.java)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        if (Settings.canDrawOverlays(context)) {
            val themeWrapper = ContextThemeWrapper(context, R.style.Theme_ChargingAnimation)
            AnimationHolderView(themeWrapper).apply {
                showView(Preferences.selectedAnimation)
                ClosableWindows.add(this)
                windowManager.addView(this, params)
            }
        }
    }

}