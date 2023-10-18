package com.example.adsmanager_androidkoltin.ads.openAds

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.adsmanager_androidkoltin.AppClass
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants.ADS_TAG
import com.example.adsmanager_androidkoltin.ads.Constants.isInterstitialIsOpen
import com.example.adsmanager_androidkoltin.ui.activities.MainActivity

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.*

class AppOpenAdResume(appClass: AppClass) : LifecycleObserver, Application.ActivityLifecycleCallbacks {
    var appOpenResumeAd: AppOpenAd? = null
    var isLoadingAppOpenResumeAd = false
    var isShowingAppOpenResumeAd = false
    private var currentActivity: Activity? = null
    init {
        try {
            appClass.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        } catch (_: Exception) {
        }
    }
    fun loadAppOpenResumeAd(context: Context) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAppOpenResumeAd || isAppOpenResumeAdAvailable()) {
            return
        }
        isLoadingAppOpenResumeAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context, context.getString(R.string.admob_open_app_ids), request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    // Called when an app open ad has loaded.
                    Log.d(ADS_TAG, "AppOpenResumeAd Ad was loaded.")
                    appOpenResumeAd = ad
                    isLoadingAppOpenResumeAd = false

                }

                override fun onAdFailedToLoad(loadAppOpenResumeAdError: LoadAdError) {
                    // Called when an app open ad has failed to load.
                    Log.d(
                        ADS_TAG,
                        "AppOpenResumeAd onAdLoadFailed  ${loadAppOpenResumeAdError.message}"
                    )
                    loadAppOpenResumeAd(context)
                    isLoadingAppOpenResumeAd = false

                }
            })
    }

    private fun isAppOpenResumeAdAvailable(): Boolean {
        return appOpenResumeAd != null
    }

    /** Shows the ad if one isn't already showing. */
    private fun showAdIfAvailable(
        activity: Activity,
        onShowAppOpenResumeAdCallback: () -> Unit
    ) {
        // If the app open ad is already showing, do not show the ad again.
        Log.d(ADS_TAG, "showAdIfAvailable: ")
        if (isShowingAppOpenResumeAd) {
            Log.d(ADS_TAG, "The app open resume ad is already showing.")
            return
        }
        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAppOpenResumeAdAvailable()) {
            Log.d(ADS_TAG, "The app open resume ad is not ready yet.")
            loadAppOpenResumeAd(activity)
            return
        }
        appOpenResumeAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                // Called when full screen content is dismissed.
                // Set the reference to null so isAppOpenResumeAdAvailable() returns false.
                Log.d(ADS_TAG, "AppOpenResumeAd dismissed fullscreen content.")
                appOpenResumeAd = null
                isShowingAppOpenResumeAd = false
                onShowAppOpenResumeAdCallback()
                loadAppOpenResumeAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when fullscreen content failed to show.
                // Set the reference to null so isAppOpenResumeAdAvailable() returns false.

                Log.d(
                    ADS_TAG,
                    "AppOpenResumeAd onAdFailedToShowFullScreenContent $adError.message"
                )
                appOpenResumeAd = null
                isShowingAppOpenResumeAd = false
                onShowAppOpenResumeAdCallback()
                loadAppOpenResumeAd(activity)
            }

            override fun onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                Log.d(ADS_TAG, "AppOpenResumeAd showed fullscreen content.")
            }
        }
        isShowingAppOpenResumeAd = true
        appOpenResumeAd?.show(activity)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.

        currentActivity?.let {
            if (it !is MainActivity && !isInterstitialIsOpen) {
                showAdIfAvailable(it) {
                    Log.d(ADS_TAG, "onMoveToForeground: ")
                }
            }else{
                Log.d(ADS_TAG, "onMoveToForeground: current activity is splash")
            }

        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (!isShowingAppOpenResumeAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }
}