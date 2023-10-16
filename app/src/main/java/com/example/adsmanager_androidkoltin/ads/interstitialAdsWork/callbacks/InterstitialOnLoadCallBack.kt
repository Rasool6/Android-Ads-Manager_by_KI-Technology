package com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks

interface InterstitialOnLoadCallBack {
    fun onAdFailedToLoad(adError:String)
    fun onAdLoaded()
    fun onPreloaded()
}