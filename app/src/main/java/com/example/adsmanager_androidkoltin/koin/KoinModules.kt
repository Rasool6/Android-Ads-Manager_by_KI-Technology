package com.example.adsmanager_androidkoltin.koin

import android.content.Context
import android.net.ConnectivityManager
import com.example.adsmanager_androidkoltin.ads.collapsballBanner.AdmobCollapseBannerAds
import com.example.adsmanager_androidkoltin.ads.nativeAds.AdmobNativeAds
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.AdmobInterstitialAds
import com.example.adsmanager_androidkoltin.ads.vvm.CollapseObserverViewModel
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
     single { CollapseObserverViewModel() }
     single { AdmobCollapseBannerAds() }

}

val modulesList = listOf(  managerModules, adsModule)

