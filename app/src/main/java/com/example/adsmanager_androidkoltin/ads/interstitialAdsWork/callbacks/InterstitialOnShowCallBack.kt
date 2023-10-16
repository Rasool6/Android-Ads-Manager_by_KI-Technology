package com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks

interface InterstitialOnShowCallBack {
    fun onAdDismissedFullScreenContent()
    fun onAdFailedToShowFullScreenContent()
    fun onAdShowedFullScreenContent()
    fun onAdImpression()
    fun onAdIsNotReadyToShow()
}