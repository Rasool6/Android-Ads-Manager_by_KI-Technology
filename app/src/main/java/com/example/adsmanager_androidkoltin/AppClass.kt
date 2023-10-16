package com.example.adsmanager_androidkoltin

import android.app.Application
import android.util.Log
import com.example.adsmanager_androidkoltin.koin.DIComponent
import com.example.adsmanager_androidkoltin.koin.modulesList
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        initKoin()
       // preLoadInterstitialAd();
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