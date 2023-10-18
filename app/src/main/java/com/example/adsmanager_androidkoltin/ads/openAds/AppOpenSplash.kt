package com.example.adsmanager_androidkoltin.ads.openAds

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants.ADS_TAG
import com.example.adsmanager_androidkoltin.ads.Constants.showAdSplashAdObserver
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback


object AppOpenSplash {


    var appOpenSplashAd: AppOpenAd? = null
    var isLoadingAppOpenSplashAd = false
    var isShowingAd = false

    /** Request an ad.  */
    fun loadAppOpenSplashAd(context: Context) {
        // Do not load ad if there is an unused ad or one is already loading.

        if (isLoadingAppOpenSplashAd || isAdAvailable()) {
            return
        }
        isLoadingAppOpenSplashAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context, context.getString(R.string.admob_open_app_ids), request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    // Called when an app open ad has loaded.
                    Log.d(ADS_TAG, "Ad was loaded.")
                    appOpenSplashAd = ad
                    isLoadingAppOpenSplashAd = false
                    showAdSplashAdObserver.postValue(true)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Called when an app open ad has failed to load.
                    Log.d(ADS_TAG, "onAdLoadFailed  ${loadAdError.message}")
                    isLoadingAppOpenSplashAd = false;
                    showAdSplashAdObserver.postValue(false)
                }
            })
    }

    /** Shows the ad if one isn't already showing. */
    fun showAdIfAvailable(
        activity: Activity,
        onShowAppOpenSplashCallback: () -> Unit
    ) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(ADS_TAG, "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d(ADS_TAG, "The app open ad is not ready yet.")
            onShowAppOpenSplashCallback()
            //   loadAd(activity)
            return
        }

        appOpenSplashAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                // Called when full screen content is dismissed.
                // Set the reference to null so isAdAvailable() returns false.
                Log.d(ADS_TAG, "Ad dismissed fullscreen content.")
                appOpenSplashAd = null
                isShowingAd = false
                onShowAppOpenSplashCallback()
                // loadAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.d(ADS_TAG, adError.message)
                appOpenSplashAd = null
                isShowingAd = false

                onShowAppOpenSplashCallback()
                // loadAd(activity)
            }

            override fun onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                Log.d(ADS_TAG, "Ad showed fullscreen content.")
            }
        }
        isShowingAd = true
        appOpenSplashAd?.show(activity)
    }


    private fun isAdAvailable(): Boolean {
        return appOpenSplashAd != null
    }


}