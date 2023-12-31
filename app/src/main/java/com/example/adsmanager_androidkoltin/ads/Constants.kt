package com.example.adsmanager_androidkoltin.ads

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd

object Constants {
/////////////////////////////
    var totalCount : Int = 0
    var showAfterCounting: Int = 2

    ////////////////////////////
    var isInterstitialLoading = false
    var isInterstitialIsOpen = false
    var mInterstitialAd: InterstitialAd? = null
//   NativeAd  //////////////////////////////////////////
    var adMobPreloadNativeAd: NativeAd? = null
    val AD_TAG = "interstitialAdLOG"
    var visited = "firstTime"


    const val ADS_TAG = "AdsLog"
    const val AD_TAG_BANNER = "AdsInformation"
    val handler = Handler()
    var next_ads_time=15000L /*120000L*/
    var isAdsPurchased = false
    const val IN_APP_PURCHASE_KEY= "InAppPurchase"
    var showAdSplashAdObserver: MutableLiveData<Boolean> = MutableLiveData()
    var checkShowAppOpenAd: Boolean = false

    fun reset(){
        mInterstitialAd = null
        isInterstitialLoading = false
    }
}