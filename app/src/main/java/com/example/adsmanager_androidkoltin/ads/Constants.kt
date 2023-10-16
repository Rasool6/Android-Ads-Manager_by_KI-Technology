package com.example.adsmanager_androidkoltin.ads

import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd

object Constants {
/////////////////////////////
    var totalCount : Int = 0
    var showAfterCounting: Int = 2

    ////////////////////////////
    var isInterstitialLoading = false
    var mInterstitialAd: InterstitialAd? = null
//   NativeAd  //////////////////////////////////////////
var adMobPreloadNativeAd: NativeAd? = null
      val AD_TAG = "interstitialAdLOG"

    fun reset(){
        mInterstitialAd = null
        isInterstitialLoading = false
    }
}