package com.funny.charging_app.ui.animation

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.funny.charging_app.R
import com.funny.charging_app.model.ClosableWindow
import com.funny.charging_app.model.ClosableWindows
import com.funny.charging_app.repository.preferences.Preferences
import com.funny.charging_app.ui.custom.AnimationHolderView

class ChargingAnimationActivity : AppCompatActivity(), ClosableWindow {
    override fun onCreate(savedInstanceState: Bundle?) {
        wakeScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charging_animation_activity)
        with(findViewById<AnimationHolderView>(R.id.root)) {
            setOnClickListener { closeWindow() }
            showView(Preferences.selectedAnimation)
        }
        ClosableWindows.add(this)
    }

    private fun wakeScreen() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        closeWindow()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeWindow()
    }

    private fun closeWindow() = ClosableWindows.close(this)

    override fun close() = finishAndRemoveTask()

}