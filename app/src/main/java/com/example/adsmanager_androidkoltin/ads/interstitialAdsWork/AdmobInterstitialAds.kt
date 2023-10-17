package com.example.adsmanager_androidkoltin.ads.interstitialAdsWork

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.adsmanager_androidkoltin.ads.Constants.AD_TAG
import com.example.adsmanager_androidkoltin.ads.Constants.isInterstitialIsOpen
import com.example.adsmanager_androidkoltin.ads.Constants.isInterstitialLoading
import com.example.adsmanager_androidkoltin.ads.Constants.mInterstitialAd
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks.InterstitialOnLoadCallBack
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks.InterstitialOnShowCallBack
import com.example.adsmanager_androidkoltin.koin.DIComponent
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobInterstitialAds {


    @SuppressLint("VisibleForTests")
    fun preLoadInterstitialAd(
        context: Context?,
        admobInterstitialIds: String,
        adEnable: Int,
        isAppPurchased: Boolean,
        mListener: InterstitialOnLoadCallBack
    ) {
        context?.let { mContext ->
//            if (isInternetConnected && adEnable != 0 && !isAppPurchased && !isInterstitialLoading && admobInterstitialIds.isNotEmpty()) {
            if (adEnable != 0 && !isAppPurchased && !isInterstitialLoading && admobInterstitialIds.isNotEmpty()) {
                if (mInterstitialAd == null) {
                    isInterstitialLoading = true
                    Log.e(AD_TAG, "admob Interstitial loading called")
                    InterstitialAd.load(
                        mContext,
                        admobInterstitialIds,
                        AdRequest.Builder().build(),
                        object : InterstitialAdLoadCallback() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                Log.e(AD_TAG, "admob Interstitial preLoad, onAdFailedToLoad")
                                isInterstitialLoading = false
                                mInterstitialAd = null
                                mListener.onAdFailedToLoad(adError.toString())
                            }

                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                Log.d(AD_TAG, "admob Interstitial preLoad, onAdLoaded")
                                isInterstitialLoading = false
                                mInterstitialAd = interstitialAd
                                mListener.onAdLoaded()
                            }
                        })
                } else {
                    Log.d(AD_TAG, "admob Interstitial onPreloaded")
                    mListener.onPreloaded()
                }

            } else {
                Log.e(AD_TAG, "adEnable = $adEnable, isAppPurchased = $isAppPurchased")
                mListener.onAdFailedToLoad("adEnable = $adEnable, isAppPurchased = $isAppPurchased")
            }
        }
    }

    fun showInterstitialAd(context: Context?, mListener: InterstitialOnShowCallBack) {
        context?.let { mContext ->
            if (mInterstitialAd != null) {
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(AD_TAG, "admob Interstitial onAdDismissedFullScreenContent")
                        mListener.onAdDismissedFullScreenContent()
                        mInterstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.e(AD_TAG, "admob Interstitial onAdFailedToShowFullScreenContent")
                        mListener.onAdFailedToShowFullScreenContent()
                        mInterstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(AD_TAG, "admob Interstitial onAdShowedFullScreenContent")
                        mListener.onAdShowedFullScreenContent()
                        mInterstitialAd = null
                    }

                    override fun onAdImpression() {
                        Log.d(AD_TAG, "admob Interstitial onAdImpression")
                        mListener.onAdImpression()
                    }
                }
                mInterstitialAd?.show(mContext as Activity)
            }
        }
    }

    fun showAndLoadInterstitialAd(context: Context?, admobInterstitialIds: String, mListener: InterstitialOnShowCallBack) {
        context?.let { mContext ->
            if (DIComponent.internetManager.isInternetConnected && mInterstitialAd != null && admobInterstitialIds.isNotEmpty()) {
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(AD_TAG, "admob Interstitial onAdDismissedFullScreenContent")
                        mListener.onAdDismissedFullScreenContent()
                        loadAgainInterstitialAd(mContext, admobInterstitialIds)
                        isInterstitialIsOpen=false
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.e(AD_TAG, "admob Interstitial onAdFailedToShowFullScreenContent")
                        mListener.onAdFailedToShowFullScreenContent()
                        mInterstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(AD_TAG, "admob Interstitial onAdShowedFullScreenContent")
                        mListener.onAdShowedFullScreenContent()
                        mInterstitialAd = null
                        isInterstitialIsOpen=true
                    }

                    override fun onAdImpression() {
                        Log.d(AD_TAG, "admob Interstitial onAdImpression")
                        mListener.onAdImpression()
                    }
                }
                mInterstitialAd?.show(mContext as Activity)
            } else {
                Log.d(
                    AD_TAG,
                    "showAndLoadInterstitialAd() InterstitialAd is not ready to show, load it again"
                )
                loadAgainInterstitialAd(mContext, admobInterstitialIds)
                mListener.onAdIsNotReadyToShow()
            }
        }
    }

    @SuppressLint("VisibleForTests")
    private fun loadAgainInterstitialAd(
        context: Context?,
        admobInterstitialIds: String
    ) {
        context?.let { mContext ->
            if (mInterstitialAd == null /*&& !isInterstitialLoading*/) {
                isInterstitialLoading = true
                InterstitialAd.load(
                    mContext,
                    admobInterstitialIds,
                    AdRequest.Builder().build(),
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.e(AD_TAG, "admob Interstitial onAdFailedToLoad: $adError")
                            isInterstitialLoading = false
                            mInterstitialAd = null
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            Log.d(AD_TAG, "admob Interstitial  Loaded")
                            isInterstitialLoading = false
                            mInterstitialAd = interstitialAd

                        }
                    })
            } else {
                Log.d(AD_TAG, "loadAgainInterstitialAd failed")
            }
        }
    }

    fun isInterstitialLoaded(): Boolean {
        return mInterstitialAd != null
    }

    fun dismissInterstitialLoaded() {
        mInterstitialAd = null
    }

}