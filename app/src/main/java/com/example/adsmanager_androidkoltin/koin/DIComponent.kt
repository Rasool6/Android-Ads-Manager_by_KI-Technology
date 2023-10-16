package com.example.adsmanager_androidkoltin.koin

import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.AdmobInterstitialAds
import com.example.adsmanager_androidkoltin.ads.vvm.InterstitialViewModel
import com.example.adsmanager_androidkoltin.extentions.InternetManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DIComponent : KoinComponent {
    val interstitialViewModel by inject<InterstitialViewModel>()

    // Utils
   // val sharedPreferenceUtils by inject<SharedPreferenceUtils>()

    // Managers
    val internetManager by inject<InternetManager>()

    // Remote Configuration
   // val remoteConfiguration by inject<RemoteConfiguration>()

    // Ads
   // val admobBannerAds by inject<AdmobBannerAds>()
  //  val admobNativeAds by inject<AdmobNativeAds>()
 //  val admobPreLoadNativeAds by inject<AdmobPreLoadNativeAds>()
    val admobInterstitialAds by inject<AdmobInterstitialAds>()
 //   val admobOpenApp by inject<AdmobOpenApp>()

}