package com.example.adsmanager_androidkoltin.ads.bannerAds

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants.AD_TAG_BANNER
import com.google.android.gms.ads.*

object AdmobBannerAd {
    private var mAdView: AdView? = null

    fun loadBannerAdMob(
        adContainer: LinearLayout,
        view: ConstraintLayout,
        context: Context?,
    ) {

         mAdView = AdView(context!!)
        mAdView?.destroy()
        (context as Activity?)?.let { getAdSize(context) }?.let { mAdView!!.setAdSize(it) }
        mAdView?.adUnitId = context.getString(R.string.admob_banner_home_ids)
        try {
            mAdView?.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAdView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.i(AD_TAG_BANNER, "AdmobBanner onAdLoaded")
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
                mAdView?.destroy()
                view.visibility = View.GONE
                Log.i(AD_TAG_BANNER, "AdmobBanner onAdFailedToLoad")
            }
        }
    }

    fun bannerOnDestroy() {
        try {
            mAdView?.destroy()
            mAdView = null
        } catch (ex: Exception) {
            Log.e(AD_TAG_BANNER, "bannerOnPause: ${ex.message}")
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