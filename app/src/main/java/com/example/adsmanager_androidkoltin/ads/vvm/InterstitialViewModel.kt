package com.example.adsmanager_androidkoltin.ads.vvm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks.InterstitialOnLoadCallBack
import com.example.adsmanager_androidkoltin.ads.interstitialAdsWork.callbacks.InterstitialOnShowCallBack
import com.example.adsmanager_androidkoltin.koin.DIComponent

class InterstitialViewModel( ) : ViewModel() {



    //    for fragment navigation
    fun goToNextFragment(navController: NavController?, nextFrg: Int, context: Context) {
        try {
            if (Constants.totalCount == 0 || Constants.totalCount >= Constants.showAfterCounting) {
                Log.d(Constants.AD_TAG, "before checkCounter: (Fragment) ${Constants.totalCount}")
                Constants.totalCount = 1
                Log.d(Constants.AD_TAG, "after checkCounter: (Fragment) ${Constants.totalCount}")
                showAndLoadInterstitialAdForFragment(navController,nextFrg,context)
            } else {
                Constants.totalCount++
                navController?.navigate(nextFrg)
                Log.d(Constants.AD_TAG, "checkCounter: (Fragment) totalCount++ = ${Constants.totalCount}")
            }
        } catch (e: Exception) {
            Log.d(Constants.AD_TAG, "${e.message}")
        }
    }
    fun backToPreviousFragment(navController: NavController?, nextFrg: Int, context: Context) {
        try {
            if (Constants.totalCount == 0 || Constants.totalCount >= Constants.showAfterCounting) {
                Log.d(Constants.AD_TAG, "before checkCounter: (Fragment) ${Constants.totalCount}")
                Constants.totalCount = 1
                Log.d(Constants.AD_TAG, "after checkCounter: (Fragment) ${Constants.totalCount}")
                showAndLoadInterstitialAdBackFragment(navController,context )
            } else {
                Constants.totalCount++
                navController?.navigateUp()
                Log.d(Constants.AD_TAG, "checkCounter: (Fragment) totalCount++ = ${Constants.totalCount}")
            }
        } catch (e: Exception) {
            Log.d(Constants.AD_TAG, "${e.message}")
        }
    }



    private fun showAndLoadInterstitialAdBackFragment(navController: NavController?, context: Context ) {

        DIComponent.admobInterstitialAds.showAndLoadInterstitialAd(
            context,
            context.getString(R.string.admob_inter_main_ids),
            object : InterstitialOnShowCallBack {
                override fun onAdDismissedFullScreenContent() {
                    navController?.navigateUp()
                    Log.d(Constants.AD_TAG, "clicked to dismissed ad: (Fragment) backToPrevious ")
                }

                override fun onAdFailedToShowFullScreenContent() {
                    Log.d(Constants.AD_TAG, "on AdFailed To Show Full ScreenContent: (Fragment) ")
                    // navController?.navigate(nextFrg)
                }

                override fun onAdShowedFullScreenContent() {}
                override fun onAdImpression() {}
                override fun onAdIsNotReadyToShow() {
                    navController?.navigateUp()
                    Log.d(Constants.AD_TAG, "(clicked) on onAdIsNotReadyToShow : (Fragment) ")
                }
            }
        )
    }

    private fun showAndLoadInterstitialAdForFragment(navController: NavController?, nextFrg: Int, context: Context  ) {

        DIComponent.admobInterstitialAds.showAndLoadInterstitialAd(
            context,
            context.getString(R.string.admob_inter_main_ids),
            object : InterstitialOnShowCallBack {
                override fun onAdDismissedFullScreenContent() {
                    navController?.navigate(nextFrg)
                    Log.d(Constants.AD_TAG, "clicked to dismissed ad: (Fragment) goToNext ")
                }

                override fun onAdFailedToShowFullScreenContent() {
                    Log.d(Constants.AD_TAG, "on AdFailed To Show Full ScreenContent: (Fragment) ")
                    // navController?.navigate(nextFrg)
                }

                override fun onAdShowedFullScreenContent() {}
                override fun onAdImpression() {}
                override fun onAdIsNotReadyToShow() {
                    navController?.navigate(nextFrg)
                    Log.d(Constants.AD_TAG, "(clicked) on onAdIsNotReadyToShow : (Fragment) ")
                }
            }
        )
    }



    // for next activity call
    fun goToNextActivity(goToNext: Class<*>, context: Context) {
        try {
            if (Constants.totalCount == 0 || Constants.totalCount >= Constants.showAfterCounting) {
               // Log.d(Constants.AD_TAG, "before checkCounter: (Activity) ${Constants.totalCount}")
                Constants.totalCount = 1
                Log.d(Constants.AD_TAG, " checkCounter: (Activity) ${Constants.totalCount}")
                showAndLoadInterstitialAdForNextActivity(goToNext, context)
            } else {
                Constants.totalCount++
                context.startActivity(Intent(context, goToNext))
                Log.d(Constants.AD_TAG, "checkCounter: (Activity) totalCount++ = ${Constants.totalCount}")
            }
        } catch (e: Exception) {
            Log.d(Constants.AD_TAG, "${e.message}")
        }
    }
    // for onBackPress activity call
    fun backToPreviousActivity(context: Context) {
        try {
            if (Constants.totalCount == 0 || Constants.totalCount >= Constants.showAfterCounting) {
            //    Log.d(Constants.AD_TAG, "before checkCounter: (Activity) ${Constants.totalCount}")
                Constants.totalCount = 1
                Log.d(Constants.AD_TAG, " checkCounter: (Activity) ${Constants.totalCount}")
                showAndLoadInterstitialAdForBackActivity(context)
            } else {
                Constants.totalCount++
                (context as Activity).finish()
                Log.d(Constants.AD_TAG, "checkCounter: (Activity) totalCount++ = ${Constants.totalCount}")
            }
        } catch (e: Exception) {
            Log.d(Constants.AD_TAG, "${e.message}")
        }
    }
    // for next frg call
    private fun showAndLoadInterstitialAdForNextActivity(goToNext: Class<*>, context: Context ) {

        DIComponent.admobInterstitialAds.showAndLoadInterstitialAd(
            context,
            context.getString(R.string.admob_inter_main_ids),
            object : InterstitialOnShowCallBack {
                override fun onAdDismissedFullScreenContent() {
                    context.startActivity(Intent(context, goToNext))
                    Log.d(Constants.AD_TAG, "clicked to dismissed ad: goToNext ")
                }

                override fun onAdFailedToShowFullScreenContent() {
                    Log.d(Constants.AD_TAG, "on AdFailed To Show Full ScreenContent: ")
                    //  context.startActivity(Intent(context, goToNext))
                }

                override fun onAdShowedFullScreenContent() {}
                override fun onAdImpression() {}
                override fun onAdIsNotReadyToShow() {
                    context.startActivity(Intent(context, goToNext))
                    Log.d(Constants.AD_TAG, "clicked on onAdIsNotReadyToShow: goToNext ")
                }
            }
        )
    }
    // for back frg call
    private fun showAndLoadInterstitialAdForBackActivity(context: Context ) {

        DIComponent.admobInterstitialAds.showAndLoadInterstitialAd(
            context,
            context.getString(R.string.admob_inter_main_ids),
            object : InterstitialOnShowCallBack {
                override fun onAdDismissedFullScreenContent() {

                            (context as Activity).finish()
                            Log.d(Constants.AD_TAG, "clicked to dismissed ad: finish ")


                }

                override fun onAdFailedToShowFullScreenContent() {
                    Log.d(Constants.AD_TAG, "on AdFailed To Show Full ScreenContent: ")
                    //  context.startActivity(Intent(context, goToNext))
                }

                override fun onAdShowedFullScreenContent() {}
                override fun onAdImpression() {}
                override fun onAdIsNotReadyToShow() {

                            (context as Activity).finish()
                            Log.d(Constants.AD_TAG, "clicked on onAdIsNotReadyToShow: finish ")

                }
            }
        )
    }


    // preLoadInterstitialAd
    fun preLoadInterstitialAd(isLoadedCallBack: (msg: Boolean) -> Unit, context: Context) {
        DIComponent.admobInterstitialAds.preLoadInterstitialAd(
            context,
            context.getString(R.string.admob_inter_main_ids),
            1,
            false, // diComponent.sharedPreferenceUtils.isAppPurchased,
             object : InterstitialOnLoadCallBack {
                override fun onAdFailedToLoad(adError: String) {
                    isLoadedCallBack(false)
                }

                override fun onAdLoaded() {
                    isLoadedCallBack(true)
                }

                override fun onPreloaded() {

                }
            }
        )
    }
}