package com.example.adsmanager_androidkoltin

import android.app.Application
import android.util.Log
import com.example.adsmanager_androidkoltin.ads.googleBilling.BillingPreferences
import com.example.adsmanager_androidkoltin.ads.openAds.AppOpenResumeAd
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

    private var appOpenResumeAd : AppOpenResumeAd? =null
    private  var billingPreference: BillingPreferences? =null
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        initKoin()
        initBilling()
        loadOpenAds()
       // preLoadInterstitialAd();
    }

    private fun loadOpenAds() {
        CoroutineScope(Dispatchers.Main).launch {
            billingPreference?.getAdsPurchaseBookmark?.collect{
                if (it==false){
                    AppOpenSplash.loadAppOpenSplashAd(this@AppClass)
                    appOpenResumeAd?.loadAppOpenResumeAd(this@AppClass)
                }
            }
        }
    }

    private fun initBilling() {
        billingPreference = BillingPreferences(this)
        appOpenResumeAd = AppOpenResumeAd(this)
    }

    private fun preLoadInterstitialAd() {
        DIComponent.interstitialViewModel.preLoadInterstitialAd(isLoadedCallBack = {

            when (it) {
                false -> {
                    Log.d("varMsg", "Ad is not loaded")
                }
                true-> {
                    Log.d("varMsg", "Ad is loaded")
                }
            }
        },this)
    }


    private fun initKoin() {
        startKoin {
            androidContext(this@AppClass)
            modules(modulesList)
        }
    }
}