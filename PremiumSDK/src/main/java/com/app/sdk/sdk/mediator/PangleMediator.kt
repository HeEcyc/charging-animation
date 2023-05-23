package com.app.sdk.sdk.mediator

import androidx.activity.ComponentActivity
import com.app.sdk.sdk.config.SdkConfig
import com.bytedance.sdk.openadsdk.api.PAGConstant
import com.bytedance.sdk.openadsdk.api.init.PAGConfig
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.init.PAGSdk.PAGInitCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest


class PangleMediator constructor(mediatorCallBack: MediatorCallBack) :
    Mediator(mediatorCallBack), PAGInterstitialAdInteractionListener {

    private var ad = PAGInterstitialRequest()

    override fun initMediator(activity: ComponentActivity) {
        if (PAGSdk.isInitSuccess()) {
            mediatorCallBack.startLoadAd(this)
        } else PAGSdk.init(activity, buildNewConfig(), object : PAGInitCallback {
            override fun success() {
                mediatorCallBack.startLoadAd(this@PangleMediator)
            }

            override fun fail(code: Int, msg: String) {
                mediatorCallBack.onError()
            }
        })
    }

    override fun loadAd(activity: ComponentActivity) {
        PAGInterstitialAd.loadAd(SdkConfig.pangleAdId, ad, object : PAGInterstitialAdLoadListener {
            override fun onError(p0: Int, p1: String?) {
                mediatorCallBack.onError()
            }

            override fun onAdLoaded(p0: PAGInterstitialAd?) {
                if (!isActual) return
                p0?.setAdInteractionListener(this@PangleMediator)
                p0?.show(activity)
            }
        })
    }

    override fun closeAd() {
        isActual = false
    }

    private fun buildNewConfig() = PAGConfig.Builder()
        .appId(SdkConfig.pangleAppId)
        .setPackageName("com.juno.ca")
        .setChildDirected(PAGConstant.PAGChildDirectedType.PAG_CHILD_DIRECTED_TYPE_CHILD)
        .setGDPRConsent(PAGConstant.PAGGDPRConsentType.PAG_GDPR_CONSENT_TYPE_NO_CONSENT)
        .setDoNotSell(PAGConstant.PAGDoNotSellType.PAG_DO_NOT_SELL_TYPE_NOT_SELL)
        .build()

    override fun onAdShowed() {
        mediatorCallBack.onDisplay()
    }

    override fun onAdClicked() {
        mediatorCallBack.onClicked()
    }

    override fun onAdDismissed() {
        mediatorCallBack.onHide()
    }
}