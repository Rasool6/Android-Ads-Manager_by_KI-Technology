package com.example.adsmanager_androidkoltin

import android.app.Application
import android.util.Log
import com.example.adsmanager_androidkoltin.ads.Constants.isAdsPurchased
import com.example.adsmanager_androidkoltin.ads.googleBilling.BillingPreferences
import com.example.adsmanager_androidkoltin.ads.openAds.AppOpenAdResume
import com.example.adsmanager_androidkoltin.ads.openAds.AppOpenSplash
import com.example.adsmanager_androidkoltin.koin.DIComponent
import com.example.adsmanager_androidkoltin.koin.modulesList
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass : Application() {

    private var appOpenAdResume: AppOpenAdResume? = null
    private var billingPreference: BillingPreferences? = null
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        initKoin()
        initBilling()
        checkIfAdsIsPurchasedOrNot()
        loadOpenAds()
        // preLoadInterstitialAd();
    }

    private fun loadOpenAds() {
        if (!isAdsPurchased) {
            AppOpenSplash.loadAppOpenSplashAd(this@AppClass)
            appOpenAdResume?.loadAppOpenResumeAd(this@AppClass)
        }
    }

    private fun checkIfAdsIsPurchasedOrNot() {
        CoroutineScope(Dispatchers.Main).launch {
            billingPreference?.getAdsPurchaseBookmark?.collect {
                if (it != null) {
                    isAdsPurchased = it
                }
            }
        }
    }

    private fun initBilling() {
        billingPreference = BillingPreferences(this)
        appOpenAdResume = AppOpenAdResume(this)
    }

//    we can preLoadInterstitialAd
    private fun preLoadInterstitialAd() {
        DIComponent.interstitialViewModel.preLoadInterstitialAd(isLoadedCallBack = {

            when (it) {
                false -> {
                    Log.d("varMsg", "Ad is not loaded")
                }

                true -> {
                    Log.d("varMsg", "Ad is loaded")
                }
            }
        }, this)
    }


    private fun initKoin() {
        startKoin {
            androidContext(this@AppClass)
            modules(modulesList)
        }
    }
}