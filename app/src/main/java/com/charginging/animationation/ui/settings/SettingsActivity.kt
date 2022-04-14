package com.charginging.animationation.ui.settings

import android.widget.CompoundButton
import com.charginging.animationation.App
import com.charginging.animationation.R
import com.charginging.animationation.base.BaseActivity
import com.charginging.animationation.databinding.SettingsActivityBinding
import com.charginging.animationation.repository.background.display.ForegroundService
import com.charginging.animationation.repository.preferences.Preferences
import com.charginging.animationation.utils.APP_LINK

class SettingsActivity : BaseActivity<SettingsActivityBinding>(R.layout.settings_activity),
    CompoundButton.OnCheckedChangeListener {

    override fun setupUI() {
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonShare.setOnClickListener { showSharedIntent() }
        binding.buttonRateUs.setOnClickListener { openLink(APP_LINK) }
        binding.buttonPrivacyPolicy.setOnClickListener { openLink(APP_LINK) }

        binding.isEnableApp.isChecked = ForegroundService.instance != null
        binding.isEnableApp.setOnCheckedChangeListener(this)

        binding.isFlashing.isChecked = Preferences.isFlashOn
        binding.isFlashing.setOnCheckedChangeListener(this)

        binding.isVibration.isChecked = Preferences.isVibrationOn
        binding.isVibration.setOnCheckedChangeListener(this)

        binding.isSound.isChecked = Preferences.isSoundOn
        binding.isSound.setOnCheckedChangeListener(this)

    }

    override fun onCheckedChanged(p0: CompoundButton, isChecked: Boolean) {
        when (p0.id) {
            R.id.isEnableApp -> with(App.instance) {
                if (isChecked) startForegroundService()
                else stopForegroundService()
            }
            R.id.isFlashing -> Preferences.isFlashOn = isChecked
            R.id.isVibration -> Preferences.isVibrationOn = isChecked
            R.id.isSound -> Preferences.isSoundOn = isChecked
        }
    }

}