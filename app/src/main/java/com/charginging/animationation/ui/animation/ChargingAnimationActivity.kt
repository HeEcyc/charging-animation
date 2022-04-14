package com.charginging.animationation.ui.animation

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.charginging.animationation.R
import com.charginging.animationation.model.ClosableWindow
import com.charginging.animationation.model.ClosableWindows
import com.charginging.animationation.repository.preferences.Preferences
import com.charginging.animationation.ui.custom.AnimationHolderView

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