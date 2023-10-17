package com.example.adsmanager_androidkoltin.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.adsmanager_androidkoltin.ads.Constants.ADS_TAG
import com.example.adsmanager_androidkoltin.ads.Constants.checkShowAppOpenAd
import com.example.adsmanager_androidkoltin.ads.Constants.isAdsPurchased
import com.example.adsmanager_androidkoltin.ads.Constants.showAdSplashAdObserver
import com.example.adsmanager_androidkoltin.ads.bannerAds.AdmobBannerAd
import com.example.adsmanager_androidkoltin.ads.googleBilling.BillingPreferences
import com.example.adsmanager_androidkoltin.ads.openAds.AppOpenSplash
import com.example.adsmanager_androidkoltin.databinding.ActivityMainBinding
import com.example.adsmanager_androidkoltin.koin.DIComponent
import com.hypersoft.billing.BillingManager
import com.hypersoft.billing.BuildConfig
import com.hypersoft.billing.status.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var bindingMain: ActivityMainBinding? = null
    private val binding get() = bindingMain!!

    private val splashDelay = 6000L
    private val productId: String = "remove_ads"
    private lateinit var userPreferences: BillingPreferences
    private val billingManager by lazy { BillingManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        userPreferences = BillingPreferences(this)
        moveWithAfterSplashTime(splashDelay)
        appOpenSplashAdsObserver()
        loadBannerAd()
        initObserver()
        initBilling()
        checkIfAdsIsPurchasedOrNot()
        preLoadAdMobInterstitialAd()



        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressCircular.visibility = View.INVISIBLE
            binding.btNext.visibility = View.VISIBLE
            binding.btNext.setOnClickListener {

                DIComponent.interstitialViewModel.goToNextActivity(FirstActivity::class.java, this)
                onPurchaseClick()
            }
        }, 9 * 1000)

    }

    private fun checkIfAdsIsPurchasedOrNot() {
        CoroutineScope(Dispatchers.Main).launch {
            userPreferences.getAdsPurchaseBookmark.collect {
                if (it != null) {
                    isAdsPurchased = it
                }
            }
        }
    }

    private fun preLoadAdMobInterstitialAd() {
        // preLoading Interstitial ads
        DIComponent.interstitialViewModel.preLoadInterstitialAd(isLoadedCallBack = {
            when (it) {
                false -> {
                    Log.d("varMsg", "Ad is not loaded")
                }

                true -> {
                    Log.d("varMsg", "Ad is loaded")
                }
            }

        }, this@MainActivity)
    }

    private fun appOpenSplashAdsObserver() {
        showAdSplashAdObserver.observe(this) {
            if (it && !checkShowAppOpenAd) {
                checkShowAppOpenAd = true
                AppOpenSplash.showAdIfAvailable(this@MainActivity) {
                    Log.d(ADS_TAG, "onCreate: SplashActivity")
                }
            } else {
                Log.d(ADS_TAG, "onCreate: ad is failed to show ")
            }
        }
    }

    private fun moveWithAfterSplashTime(splashDelay: Long) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(splashDelay)
            if (!checkShowAppOpenAd) {
                checkShowAppOpenAd = true
                Log.d(ADS_TAG, "moveWithAfterSplashTime: ")
            } else {
                Log.d(ADS_TAG, "splash time done: ")
            }
        }

    }

    private fun loadBannerAd() {

        if (!isAdsPurchased) {
            AdmobBannerAd.loadBannerAdMob(
                binding.bannerAd.adContainer,
                binding.bannerID,
                this@MainActivity
            )
        } else {
            binding.bannerID.visibility = View.GONE
        }


    }

    private fun initObserver() {
        State.billingState.observe(this) {
            Log.d("BillingManager", "initObserver: $it")
        }
    }

    private fun initBilling() {
        if (BuildConfig.DEBUG) {
            billingManager.startConnection(billingManager.getDebugProductIDList()) { _, alreadyPurchased, _ ->
                if (alreadyPurchased) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userPreferences.saveAdsPurchaseBookmark(true)
                    }
                }
            }
        } else {
            billingManager.startConnection(listOf(productId)) { _, alreadyPurchased, _ ->
                if (alreadyPurchased) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userPreferences.saveAdsPurchaseBookmark(true)
                    }

                }
            }
        }
    }

    private fun onPurchaseClick() {
        billingManager.makePurchase(this) { isSuccess, message ->
            if (isSuccess) {
                //restart your app here
                this.recreate()
            }

        }
    }
}