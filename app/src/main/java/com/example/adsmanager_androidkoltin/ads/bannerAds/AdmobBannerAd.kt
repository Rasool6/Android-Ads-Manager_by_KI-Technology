package com.example.adsmanager_androidkoltin.ads.bannerAds

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants.ADS_TAG
import com.google.android.gms.ads.*

object AdmobBannerAd {

    fun loadBannerAdMob(
        adContainer: LinearLayout,
        view: ConstraintLayout,
        context: Context?,
    ) {

        val mAdView = AdView(context!!)
        (context as Activity?)?.let { getAdSize(context) }?.let { mAdView.setAdSize(it) }
        mAdView.adUnitId = context.getString(R.string.admob_banner_home_ids)
        try {
            mAdView.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.i(ADS_TAG, "AdmobBanner onAdLoaded")
                view.visibility = View.VISIBLE
                try {
                    adContainer.removeAllViews()
                    adContainer.addView(mAdView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                mAdView.destroy()
                view.visibility = View.GONE
                Log.i(ADS_TAG, "AdmobBanner onAdFailedToLoad")
            }
        }
    }


    private fun getAdSize(context: Activity): AdSize {
        val display = context.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }
}