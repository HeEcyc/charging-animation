package com.kapi.ca.ui.home

import com.kapi.ca.base.BaseViewModel
import com.kapi.ca.repository.background.display.BatteryLevelReceiver

class HomeViewModel : BaseViewModel() {

    val percent = BatteryLevelReceiver.batteryLevel

}