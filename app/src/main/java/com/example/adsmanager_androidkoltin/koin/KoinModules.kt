package com.example.adsmanager_androidkoltin.koin

import android.content.Context
import android.net.ConnectivityManager
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.AdmobNativeAds
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.AdmobInterstitialAds
import com.example.adsmanager_androidkoltin.ads.vvm.InterstitialViewModel
import com.example.adsmanager_androidkoltin.extentions.InternetManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val managerModules = module {
    single { InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
}


private val adsModule = module {
     single { AdmobInterstitialAds() }
     single { InterstitialViewModel() }
     single { AdmobNativeAds() }

}

val modulesList = listOf(  managerModules, adsModule)

