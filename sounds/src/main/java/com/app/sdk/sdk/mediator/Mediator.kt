package com.app.sdk.sdk.mediator

import androidx.appcompat.app.AppCompatActivity

abstract class Mediator(val mediatorCallBack: MediatorCallBack) {

    abstract fun initMediator(activity: AppCompatActivity)

    abstract fun showAd(activity: AppCompatActivity)

    interface MediatorCallBack {

        fun onCompleteLoad(mediator: Mediator)

        fun onError()

        fun onHide()

        fun onClicked()

        fun onDisplay()

    }


}