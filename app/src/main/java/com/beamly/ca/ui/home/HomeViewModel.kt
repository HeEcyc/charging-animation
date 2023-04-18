package com.beamly.ca.ui.home

import com.beamly.ca.base.BaseViewModel
import com.beamly.ca.repository.background.display.BatteryLevelReceiver

class HomeViewModel : BaseViewModel() {

    val percent = BatteryLevelReceiver.batteryLevel

}